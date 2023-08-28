package com.pw.ability.task.service.impl;

import com.pw.base.utils.CronUtils;
import com.pw.ability.task.service.JobService;
import lombok.extern.log4j.Log4j2;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Random;

/**
 * @author bool
 */
@Log4j2
@Service
public class JobServiceImpl implements JobService {

    /**
     * Quartz定时任务核心的功能实现类
     */
    private Scheduler scheduler;

    /**
     * 注入
     * @param schedulerFactoryBean
     */
    public JobServiceImpl(@Autowired SchedulerFactoryBean schedulerFactoryBean) {
        scheduler = schedulerFactoryBean.getScheduler();
    }


    @Override
    public void addCronJob(Class jobClass, String jobName, String jobGroup, String cron, String data) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail != null) {
                log.info("++++++++++任务：{} 已存在", jobName);
                this.deleteJob(jobName, jobGroup);
            }

            log.info("++++++++++构建任务：{},{},{},{},{} ", jobClass.toString(), jobName, jobGroup, cron, data);

            //构建job信息
            jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroup).build();
            //用JopDataMap来传递数据
            jobDetail.getJobDataMap().put(TASK_DATA, data);

            //表达式调度构建器(即任务执行的时间,每5秒执行一次)
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);

            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup).withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addCronJob(Class jobClass, String jobName, String jobGroup, String data) {

        // 随机3-8秒后执行
        java.util.Calendar cl = java.util.Calendar.getInstance();
        cl.setTimeInMillis(System.currentTimeMillis());
        cl.add(Calendar.SECOND, 3 + new Random().nextInt(5));

        this.addCronJob(jobClass, jobName, jobGroup, CronUtils.dateToCron(cl.getTime()), data);
    }


    @Override
    public void pauseJob(String jobName, String jobGroup) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            scheduler.pauseTrigger(triggerKey);
            log.info("++++++++++暂停任务：{}", jobName);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resumeJob(String jobName, String jobGroup) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            scheduler.resumeTrigger(triggerKey);
            log.info("++++++++++重启任务：{}", jobName);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteJob(String jobName, String jobGroup) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName,jobGroup);
            scheduler.deleteJob(jobKey);
            log.info("++++++++++删除任务：{}", jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
