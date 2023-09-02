package com.yf.system.modules.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yf.system.modules.role.entity.SysRole;
import com.yf.system.modules.user.entity.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
* 用户角色Mapper
* </p>
*
* @author 聪明笨狗
* @since 2020-04-13 16:57
*/
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 查找用户的角色列表
     * @param userId
     * @return
     */
    List<SysRole> listByUser(@Param("userId") String userId);

    /**
     * 查找用户的权限标签
     * @param userId
     * @return
     */
    List<String> findUserPermission(@Param("userId") String userId);

    /**
     * 查找权限最大的一个角色
     * @param userId
     * @return
     */
    SysUserRole findMaxRole(@Param("userId") String userId);

    /**
     * 统计数量
     * @param userIds
     * @param roleLevel
     * @return
     */
    int countWithLevel(@Param("userIds") List<String> userIds, @Param("roleLevel") Integer roleLevel);


    /**
     * 查找最大的角色级别
     * @param userId
     * @return
     */
    int findMaxLevel(@Param("userId") String userId);
}
