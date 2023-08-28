package com.pw.ability.redis.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pw.ability.redis.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author bool
 */
@Service
public class RedisServiceImpl implements RedisService {

    /**
     * 锁相关内容
     */
    private static final String lock = "locked";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 删除一个或多个缓存
     * @param key
     */
    @Override
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                Collection keys = CollectionUtils.arrayToList(key);
                redisTemplate.delete(keys);
            }
        }
    }

    @Override
    public JSONObject getJson(String key) {
        String json = this.get(key);

        if(!StringUtils.isBlank(json)){
            return JSON.parseObject(json);
        }

        return null;
    }

    @Override
    public String getString(String key) {
        return get(key);
    }


    /**
     * 获得一个锁
     *
     * @param key
     * @return
     */
    @Override
    public boolean tryLock(String key, Long ms) {
        // 获得一个50毫秒的锁
        return redisTemplate.opsForValue().setIfAbsent(key, lock, ms, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取缓存内容
     *
     * @param key
     * @return
     */
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }


    /**
     * 设置缓存值
     *
     * @param key
     * @param data
     * @return
     */
    @Override
    public boolean set(String key, String data) {
        try {
            redisTemplate.opsForValue().set(key, data);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 设置缓存
     * @param key
     * @param value
     * @param time 过期秒数
     * @return
     */
    @Override
    public boolean set(String key, String value, Long time) {


        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                this.set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean putList(String key, String value) {
        redisTemplate.opsForList().leftPush(key, value);
        return true;
    }

    @Override
    public boolean removeList(String key, String value) {
        redisTemplate.opsForList().remove(key, 0, value);
        return true;
    }

    @Override
    public List<String> findList(String key) {
        return  redisTemplate.opsForList().range(key, 0, -1);
    }
}
