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

package org.glass.log;

import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.glass.util.Dates;

/**
 * @author damien bourdette
 */
public class Log {
    private LogLevel level;

    private Date date;

    private String message;

    private String stackTrace;

    private Log() {

    }

    public static Log message(LogLevel level, String message) {
        Log log = new Log();

        log.date = new Date();
        log.level = level;
        log.message = message;

        return log;
    }

    public static Log exception(LogLevel level, String message, Throwable e) {
        Log log = message(level, message);

        log.stackTrace = ExceptionUtils.getFullStackTrace(e);

        return log;
    }

    public LogLevel getLevel() {
        return level;
    }

    public Date getDate() {
        return Dates.copy(date);
    }

    public String getMessage() {
        return message;
    }

    public String getStackTrace() {
        return stackTrace;
    }
}
