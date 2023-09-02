package com.yf.system.modules.config.enums;

/**
 * 功能名称ID枚举，对应数据库el_cfg_switch，用于系统逻辑判断
 */
public interface FuncSwitch {

    // 人脸识别
    String FACE_LOGIN = "faceLogin";

    // 企业微信登录
    String CROP_LOGIN = "cropLogin";

    // 是否T下线的
    String LOGIN_TICK = "loginTick";

    // 手机号登录
    String MOBILE_LOGIN = "mobileLogin";

    // 注册是否要审核
    String USER_AUDIT = "userAudit";

    // 是否指定部门
    String USER_DEPT_TYPE = "userDeptType";

    // 指定的部门
    String USER_DEPT_CODE = "userDeptCode";

    // 是否开启注册
    String USER_REG = "userReg";

    // 微信登录
    String WECHAT_LOGIN = "wechatLogin";



}
