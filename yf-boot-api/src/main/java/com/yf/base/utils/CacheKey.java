package com.yf.base.utils;

import java.text.MessageFormat;

/**
 * 缓存Key定义
 * @author bool
 */
public interface CacheKey {

    /**
     * 网站
     */
    String SITE = "sys:site";

    /**
     * 菜单路由
     */
    String MENU = "sys:menu";

    /**
     * 数据字典
     */
    String DICT = "sys:dict";

    /**
     * 用户token
     */
    String TOKEN = "user:token";


    /**
     * 上传配置
     */
    String UPLOAD = "sys:upload";

    String SWITCH = "sys:switch";

    /**
     * 课件解锁，用于在解锁下一个课件时写入缓存
     * @param userId
     * @param courseId
     * @return
     */
    static String unlockKey(String userId, String courseId){
        return MessageFormat.format("sys:file:unlock:{0}-{1}", userId, courseId);
    }
}
