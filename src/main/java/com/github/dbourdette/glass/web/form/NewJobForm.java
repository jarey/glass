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

package com.github.dbourdette.glass.web.form;

import javax.validation.constraints.NotNull;

import com.github.dbourdette.glass.job.util.JobDataMapUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;

/**
 * Form for job editing
 */
public class NewJobForm {

    @NotEmpty
    private String group = "DEFAULT";

    @NotEmpty
    private String name;

    @NotNull
    private Class<? extends Job> clazz;

    private String dataMap;

    public NewJobForm() {
    }

    public NewJobForm(JobDetail jobDetail) {
        super();
        this.group = jobDetail.getKey().getGroup();
        this.name = jobDetail.getKey().getName();
        this.clazz = jobDetail.getJobClass();
        this.dataMap = JobDataMapUtils.toProperties(jobDetail.getJobDataMap(), "\n");
    }

    /**
     * Builds a {@link JobDetail} using internal state
     * 
     * @return
     */
    public JobDetail getJobDetails() {
        return JobBuilder.newJob(clazz)
                         .withIdentity(name.trim(), group.trim())
                         .usingJobData(JobDataMapUtils.fromProperties(dataMap))
                         .storeDurably()
                         .build();
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<? extends Job> clazz) {
        this.clazz = clazz;
    }

    public String getDataMap() {
        return dataMap;
    }

    public void setDataMap(String dataMap) {
        this.dataMap = dataMap;
    }

}
