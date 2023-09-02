package com.yf.system.modules.user;


import com.yf.ability.shiro.dto.SysUserLoginDTO;
import com.yf.base.api.api.ApiError;
import com.yf.base.api.exception.ServiceException;
import org.apache.shiro.SecurityUtils;

import java.util.List;

/**
 * 用户静态工具类
 * @author bool
 */
public class UserUtils {


    /**
     * 获取用户信息
     * @param throwable
     * @return
     */
    public static SysUserLoginDTO getUser(boolean throwable){
        try {
            return ((SysUserLoginDTO) SecurityUtils.getSubject().getPrincipal());
        }catch (Exception e){
            if(throwable){
                throw new ServiceException(ApiError.ERROR_10010002);
            }
            return null;
        }
    }

    /**
     * 获取当前登录用户的ID
     * @param throwable
     * @return
     */
    public static String getUserId(boolean throwable){
        SysUserLoginDTO user = getUser(throwable);
        if(user!=null){
            return user.getId();
        }
        return null;
    }

    /**
     * 是否管理员
     * @return
     */
    public static String departCode(){
        SysUserLoginDTO user = getUser(false);
        if(user!=null){
            return user.getDeptCode();
        }
        return null;
    }


    /**
     * 获取用户的角色列表
     * @return
     */
    public static List<String> getRoles(){
        SysUserLoginDTO user = getUser(false);
        if(user!=null){
            return user.getRoles();
        }
        return null;
    }

    /**
     * 权限类型
     * @return
     */
    public static Integer getDataScope(){
        SysUserLoginDTO user = getUser(false);
        if(user!=null){
            return user.getDataScope();
        }
        return null;
    }

    /**
     * 角色级别
     * @return
     */
    public static Integer getRoleLevel(){
        SysUserLoginDTO user = getUser(false);
        if(user!=null){
            return user.getRoleLevel();
        }
        return 0;
    }

    /**
     * 获取当前登录用户的ID，默认是会抛异常的
     * @return
     */
    public static String getUserId(){
        return getUserId(true);
    }
}
