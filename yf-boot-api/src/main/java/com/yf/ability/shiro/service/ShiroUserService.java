package com.yf.ability.shiro.service;


import com.yf.ability.shiro.dto.SysUserLoginDTO;

import java.util.List;

/**
 * 获取用户角色权限信息，使用时必须要实现此方法才能进行权限控制
 * @author van
 */
public interface ShiroUserService {

    /**
     * 查找用户权限
     * @param userId
     * @return
     */
    List<String> permissions(String userId);

    /**
     * 查找用户角色列表
     * @param userId
     * @return
     */
    List<String> roles(String userId);

    /**
     * 获取会话缓存
     * @param token
     * @return
     */
    SysUserLoginDTO token(String token);
}
