package com.yf.system.modules.menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yf.system.modules.menu.dto.response.MenuTreeRespDTO;
import com.yf.system.modules.menu.dto.response.RouteRespDTO;
import com.yf.system.modules.menu.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
* 系统菜单Mapper
* </p>
*
* @author 聪明笨狗
* @since 2021-03-02 13:09
*/
public interface SysMenuMapper extends BaseMapper<SysMenu> {


    /**
     * 列出菜单树结构
     * @return
     */
    List<MenuTreeRespDTO> listTree();


    /**
     * 获得管理路由表
     * @param roleIds
     * @return
     */
    List<RouteRespDTO> listMenuByRoles(@Param("roleIds") List<String> roleIds);


    /**
     * 获得全部功能权限表
     * @param roleIds
     * @return
     */
    List<String> listPermissionByRoles(@Param("roleIds") List<String> roleIds);


    /**
     * 查找某个目标下最大的序号
     * @param parentId
     * @return
     */
    int findMaxSort(@Param("parentId") String parentId);

    /**
     * 批量加大排序
     * @param parentId
     * @return
     */
    int incrSort(@Param("parentId") String parentId, @Param("start") int start);

}
