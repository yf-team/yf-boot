package com.yf.ability.redis.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.yf.ability.redis.service.RedisService;
import com.yf.base.utils.jackson.JsonHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
     * @param keys
     */
    @Override
    public void del(String... keys) {
        if (keys != null && keys.length > 0) {
            if (keys.length == 1) {
                redisTemplate.delete(keys[0]);
            } else {
                List<String> list = Arrays.asList(keys);
                redisTemplate.delete(list);
            }
        }
    }

    @Override
    public Map<String,Object> getJson(String key) {
        String json = this.get(key);

        if(!StringUtils.isBlank(json)){
            return JsonHelper.parseObject(json, new TypeReference<Map<String,Object>>(){});
        }

        return null;
    }

    @Override
    public String getString(String key) {
        return get(key);
    }

    /**
     * 获得锁
     * @param key 锁key
     * @param ms 失效时间：毫秒
     * @param tryCount 尝试次数
     * @param tryWait 尝试间隔：毫秒
     * @return
     */
    @Override
    public boolean tryLock(String key, Long ms, int tryCount, long tryWait) {

        int hasTry = 0;

        // 是否获得成功
        Boolean hasGet = null;

        // 获得一个锁
        while ((hasGet==null || !hasGet) && hasTry<=tryCount){

            // 第二次以后进行等待
            if(hasTry > 0 ){
                try {
                    Thread.sleep(tryWait);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            hasGet = redisTemplate.opsForValue().setIfAbsent(key, lock, ms, TimeUnit.MILLISECONDS);
            hasTry++;
        }

        return hasGet!=null && hasGet;
    }


    /**
     * 获得一个锁
     *
     * @param key
     * @return
     */
    @Override
    public boolean tryLock(String key, Long ms) {
        return tryLock(key, ms, 5, 500);
    }

    /**
     * 释放锁
     * @param key
     * @return
     */
    @Override
    public void unlock(String key) {
          redisTemplate.delete(key);
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

    @Override
    public boolean hasKey(String key) {
        Boolean has = redisTemplate.hasKey(key);
        return has!=null && has;
    }
}
