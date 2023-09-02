package com.yf.system.modules.user.enums;


/**
 * 用户待审核状态
 * @author bool
 * @date 2019-10-30 13:11
 */
public interface UserState {

    /**
     * 正常状态
     */
    Integer NORMAL = 0;

    /**
     * 被禁用
     */
    Integer DISABLED = 1;

    /**
     * 待审核
     */
    Integer AUDIT = 2;


}
