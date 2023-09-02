package com.yf.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 定时任务配置类
 * @author bool
 */
@Log4j2
@Configuration
public class QuartzConfig {

    /**
     * 配置基础的系统任务
     */
    @PostConstruct
    public void initSystem(){
        log.error("+++++开始配置任务...");
    }



}
