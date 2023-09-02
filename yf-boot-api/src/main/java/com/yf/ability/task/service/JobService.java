package com.yf.ability.task.service;

/**
 * 任务业务类，用于动态处理任务信息
 * @author bool
 * @date 2020/11/29 下午2:17
 */
public interface JobService {


    /**
     * 任务数据
     */
    String TASK_DATA = "taskData";

    /**
     * 添加定时任务
     * @param jobClass
     * @param jobName
     * @param jobGroup
     * @param cron
     * @param data
     */
    void addCronJob(Class jobClass, String jobName, String jobGroup, String cron, String data);

    /**
     * 添加立即执行的任务
     * @param jobClass
     * @param jobName
     * @param jobGroup
     * @param data
     */
    void addCronJob(Class jobClass, String jobName, String jobGroup, String data);

    /**
     * 暂停任务
     * @param jobName
     * @param jobGroup
     */
    void pauseJob(String jobName, String jobGroup);

    /**
     * 恢复任务
     * @param triggerName
     * @param triggerGroup
     */
    void resumeJob(String triggerName, String triggerGroup);

    /**
     * 删除job
     * @param jobName
     * @param jobGroup
     */
    void deleteJob(String jobName, String jobGroup);
}
