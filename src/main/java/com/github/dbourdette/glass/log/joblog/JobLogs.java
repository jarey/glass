/*
 * Copyright 2011 Damien Bourdette
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.dbourdette.glass.log.joblog;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;

import com.github.dbourdette.glass.job.annotation.JobArgumentBean;
import com.github.dbourdette.glass.log.execution.CurrentJobExecution;
import com.github.dbourdette.glass.util.Page;
import com.github.dbourdette.glass.util.Query;

/**
 * Sends logs to log store and through slf4j.
 *
 * @author damien bourdette
 */
public class JobLogs {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobLogs.class);

    private static final String[] EMPTY_ARGS = new String[]{};

    private static ThreadLocal<JobLogLevel> threadLevel = new ThreadLocal<JobLogLevel>();

    public static JobLogStore jobLogStore;

    /**
     * Sets current level fot current thread.
     */
    public static void setLevel(JobLogLevel level) {
        threadLevel.set(level);
    }

    /**
     * Try to read given string and set level accordingly.
     * If provided value is empty or misspelled, then level is defaulted to WARN.
     */
    public static void setLevel(String level) {
        if (StringUtils.isEmpty(level)) {
            setLevel(JobLogLevel.WARN);

            return;
        }

        try {
            setLevel(JobLogLevel.valueOf(level));
        } catch (Exception e) {
            LOGGER.warn("{} has an incorrect value ({}) for job, defaulting to WARN", JobArgumentBean.LOG_LEVEL_ARGUMENT, level);

            setLevel(JobLogLevel.WARN);
        }
    }

    public static void setDefaultLevel() {
        setLevel(JobLogLevel.WARN);
    }

    public static void debug(String message) {
        log(JobLogLevel.DEBUG, message);

        LOGGER.debug(message);
    }

    public static void debug(String format, Object... args) {
        log(JobLogLevel.DEBUG, format(format, args));

        LOGGER.debug(format, args);
    }

    public static void debug(String message, Throwable throwable) {
        log(JobLogLevel.DEBUG, message, throwable);

        LOGGER.debug(message, throwable);
    }

    public static void info(String message) {
        log(JobLogLevel.INFO, message);

        LOGGER.info(message);
    }

    public static void info(String format, Object... args) {
        log(JobLogLevel.INFO, format(format, args));

        LOGGER.info(format, args);
    }

    public static void info(String message, Throwable throwable) {
        log(JobLogLevel.INFO, message, throwable);

        LOGGER.info(message, throwable);
    }

    public static void warn(String message) {
        log(JobLogLevel.WARN, message);

        LOGGER.warn(message);
    }

    public static void warn(String format, Object... args) {
        log(JobLogLevel.WARN, format(format, args));

        LOGGER.warn(format, args);
    }

    public static void warn(String message, Throwable throwable) {
        log(JobLogLevel.WARN, message, throwable);

        LOGGER.warn(message, throwable);
    }

    public static void error(String message) {
        log(JobLogLevel.ERROR, message);

        LOGGER.error(message);
    }

    public static void error(String format, Object... args) {
        log(JobLogLevel.ERROR, format(format, args));

        LOGGER.error(format, args);
    }

    public static void error(String message, Throwable throwable) {
        log(JobLogLevel.ERROR, message, throwable);

        LOGGER.error(message, throwable);
    }

    public static Page<JobLog> getLogs(Long executionId, Query query) {
        return jobLogStore.getLogs(executionId, query);
    }

    public static Page<JobLog> getLogs(Query query) {
        return jobLogStore.getLogs(query);
    }

    public static void clear() {
        jobLogStore.clear();
    }

    private static void log(JobLogLevel level, String message) {
        log(level, message, EMPTY_ARGS);
    }

    private static void log(JobLogLevel level, String format, Object... args) {
        JobLogLevel currentLevel = threadLevel.get();

        if (level.ordinal() >= currentLevel.ordinal()) {
            jobLogStore.add(JobLog.message(CurrentJobExecution.get(), level, format(format, args)));
        }

        markExecutionIfNeeded(level);
    }

    private static void log(JobLogLevel level, String message, Throwable throwable) {
        JobLogLevel currentLevel = threadLevel.get();

        if (level.ordinal() >= currentLevel.ordinal()) {
            jobLogStore.add(JobLog.exception(CurrentJobExecution.get(), level, message, throwable));
        }

        markExecutionIfNeeded(level);
    }

    private static String format(String format, Object... args) {
        if (args.length == 0) {
            return format;
        }

        return MessageFormatter.arrayFormat(format, args).getMessage();
    }

    private static void markExecutionIfNeeded(JobLogLevel level) {
        if (level == JobLogLevel.WARN) {
            CurrentJobExecution.get().warn();
        } else if (level == JobLogLevel.ERROR) {
            CurrentJobExecution.get().error();
        }
    }
}
