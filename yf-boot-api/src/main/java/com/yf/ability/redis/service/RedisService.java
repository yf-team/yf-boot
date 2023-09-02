package com.yf.ability.redis.service;

import java.util.List;
import java.util.Map;

/**
 * Redis公共服务
 * @author bool
 */
public interface RedisService {

    /**
     * 获得锁
     * @param key 锁key
     * @param ms 失效时间：毫秒
     * @param tryCount 尝试次数
     * @param tryWait 尝试间隔：毫秒
     * @return
     */
    boolean tryLock(String key, Long ms, int tryCount, long tryWait);

    /**
     * 获得一个锁：尝试5次，每次等待500ms
     * @param key
     * @param ms 锁定毫秒数
     * @return
     */
    boolean tryLock(String key, Long ms);

    /**
     * 释放锁
     * @param key
     * @return
     */
    public void unlock(String key);

    /**
     * 删除缓存key
     * @param keys
     */
    void del(String ... keys);

    /**
     * 获取缓存内容
     * @param key
     * @return
     */
    Map<String,Object> getJson(String key);

    /**
     * 获取缓存内容
     * @param key
     * @return
     */
    String getString(String key);


    /**
     * 设置缓存值
     * @param key
     * @param data
     * @return
     */
    boolean set(String key,  String data);

    /**
     * 设置缓存
     * @param key
     * @param value
     * @param time
     * @return
     */
    boolean set(String key, String value, Long time);

    /**
     * 加入列表
     * @param key
     * @param value
     * @return
     */
    boolean putList(String key, String value);

    /**
     * 移除列表
     * @param key
     * @param value
     * @return
     */
    boolean removeList(String key, String value);

    /**
     * 查找列表
     * @param key
     * @return
     */
    List<String> findList(String key);

    /**
     * 包含值
     * @param key
     * @return
     */
    boolean hasKey(String key);

}
