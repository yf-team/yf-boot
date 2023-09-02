package com.yf.system.modules.menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yf.system.modules.depart.dto.request.DepartSortReqDTO;
import com.yf.system.modules.menu.dto.SysMenuDTO;
import com.yf.system.modules.menu.dto.response.MenuTreeRespDTO;
import com.yf.system.modules.menu.dto.response.RouteRespDTO;
import com.yf.system.modules.menu.entity.SysMenu;

import java.util.List;

/**
* <p>
* 系统菜单业务类
* </p>
*
* @author 聪明笨狗
* @since 2021-03-02 13:09
*/
public interface SysMenuService extends IService<SysMenu> {


    /**
     * 根据角色
     * @param roleIds
     * @return
     */
    List<RouteRespDTO> listMenuByRoles(List<String> roleIds);

    /**
     * 获取权限
     * @param roleIds
     * @return
     */
    List<String> listPermissionByRoles(List<String> roleIds);

    /**
     * 列出菜单树结构
     * @return
     */
    List<MenuTreeRespDTO> listTree();

    /**
     * 拖拽排序
     * @param reqDTO
     */
    void sort(DepartSortReqDTO reqDTO);

    /**
     * 保存菜单
     * @param reqDTO
     */
    void save(SysMenuDTO reqDTO);

    /**
     * 删除菜单
     * @param ids
     */
    void delete(List<String> ids);
}


