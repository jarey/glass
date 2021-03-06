drop table glass_execution_log;
drop table glass_log;
drop sequence glass_sequence;

CREATE SEQUENCE glass_sequence;

CREATE TABLE glass_job_execution
  (
    id number(19,0) NOT NULL,
    startDate timestamp NOT NULL,
    endDate timestamp,
    ended varchar(1) NOT NULL,
    jobGroup VARCHAR(200) NOT NULL,
    jobName VARCHAR(200) NOT NULL,
    triggerGroup VARCHAR(200) NOT NULL,
    triggerName VARCHAR(200) NOT NULL,
    jobClass VARCHAR(255) NOT NULL,
    dataMap CLOB,
    result VARCHAR(20) NOT NULL
);

CREATE TABLE glass_job_log
  (
    id number(19,0) NOT NULL,
    executionId number(19,0) NOT NULL,
    jobClass VARCHAR(255) NOT NULL,
    logLevel VARCHAR(20) NOT NULL,
    logDate timestamp NOT NULL,
    jobGroup VARCHAR(200) NOT NULL,
    jobName VARCHAR(200) NOT NULL,
    triggerGroup VARCHAR(200) NOT NULL,
    triggerName VARCHAR(200) NOT NULL,
    message CLOB,
    stackTrace CLOB,
    rootCause CLOB
);

create index idx_glas_job_exec on glass_job_execution(startDate);
create index idx_glas_job_exec_job on glass_job_execution(jobGroup, jobName);
create index idx_glas_job_log on glass_job_log(logDate);
create index idx_glas_job_log_exec on glass_job_log(executionId);