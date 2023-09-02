package com.yf.system.modules.user.enums;


/**
 * 登录类型
 * @author bool
 * @date 2019-10-30 13:11
 */
public interface LoginType {

    /**
     * 微信登录
     */
    String WECHAT = "wechat";


    /**
     * 企业微信
     */
    String CROP_WECHAT = "crop-wechat";

    /**
     * 钉钉
     */
    String DING_TALK = "ding-talk";

    /**
     * 手机号
     */
    String MOBILE = "mobile";

}
