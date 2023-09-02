package com.yf.ability.captcha.service.impl;


import com.yf.ability.captcha.service.CaptchaService;
import com.yf.ability.redis.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 验证码业务类
 * @author bool
 * @date 2020-02-21 10:05
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Autowired
    private RedisService redisService;

    /**
     * 验证码缓存前缀
     */
    private static final String CAPTCHA_PREFIX = "sys:captcha:";

    @Override
    public void saveCaptcha(String key, String value) {
        redisService.set(appendKey(key), value, 300L);
    }

    @Override
    public boolean checkCaptcha(String key, String input) {

        // 完整KEY
        String fullKey = appendKey(key);

        String value = redisService.getString(fullKey);

        // 校验
        boolean result = StringUtils.isNotBlank(value) && value.equalsIgnoreCase(input);

        // 验证正确就清除
        if(result) {
            redisService.del(fullKey);
        }

        return result;
    }

    /**
     * 组合KEY
     * @param key
     * @return
     */
    private String appendKey(String key){
        return new StringBuffer(CAPTCHA_PREFIX).append(key).toString();
    }
}
