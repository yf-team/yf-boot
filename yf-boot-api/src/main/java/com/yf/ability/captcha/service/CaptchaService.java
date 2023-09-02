package com.yf.ability.captcha.service;

/**
 * 验证码业务类
 * @author bool
 * @date 2020-02-17 09:43
 */
public interface CaptchaService {

    /**
     * 保存验证码信息到Redis
     * @param key
     * @param value
     */
    void saveCaptcha(String key, String value);

    /**
     * 校验验证码内容是否正确
     * @param key
     * @param input
     * @return
     */
    boolean checkCaptcha(String key, String input);
}
