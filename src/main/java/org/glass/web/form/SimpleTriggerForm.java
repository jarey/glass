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

package org.glass.web.form;

import org.glass.job.JobUtils;
import org.joda.time.DateTime;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.util.Date;

/**
 * @author damien bourdette
 * @version \$Revision$
 */
public class SimpleTriggerForm {
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Future
    private Date startTime;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date endTime;

    @Min(-1)
    private Integer repeatCount;

    @NotNull
    @Min(0)
    private Integer intervalInMilliseconds;

    private String dataMap;

    public SimpleTriggerForm() {
    }

    public SimpleTriggerForm(Trigger trigger) {
        this.startTime = trigger.getStartTime();
        this.endTime = trigger.getEndTime();
        this.dataMap = JobUtils.toProperties(trigger.getJobDataMap(), "\n");
        this.repeatCount = ((SimpleTrigger) trigger).getRepeatCount();
        this.intervalInMilliseconds = (int) ((SimpleTrigger) trigger).getRepeatInterval();
    }

    public Trigger getTrigger(Trigger trigger) throws ParseException {
        if (repeatCount == null) {
            repeatCount = 0;
        }

        if (intervalInMilliseconds == null) {
            intervalInMilliseconds = 0;
        }

        if (startTime == null) {
            startTime = new DateTime().plusSeconds(1).toDate();
        }

        TriggerBuilder builder = TriggerBuilder.newTrigger().forJob(trigger.getJobKey().getName(), trigger.getJobKey().getGroup())
                .withIdentity(trigger.getKey().getName(), trigger.getKey().getGroup())
                .startAt(startTime).endAt(endTime)
                .usingJobData(JobUtils.fromProperties(dataMap));

        if (repeatCount == -1) {
            builder.withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatForever()
                    .withIntervalInMilliseconds(intervalInMilliseconds));
        } else {
            builder.withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(repeatCount)
                    .withIntervalInMilliseconds(intervalInMilliseconds));
        }

        return builder.build();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(Integer repeatCount) {
        this.repeatCount = repeatCount;
    }

    public Integer getIntervalInMilliseconds() {
        return intervalInMilliseconds;
    }

    public void setIntervalInMilliseconds(Integer intervalInMilliseconds) {
        this.intervalInMilliseconds = intervalInMilliseconds;
    }

    public String getDataMap() {
        return dataMap;
    }

    public void setDataMap(String dataMap) {
        this.dataMap = dataMap;
    }
}
