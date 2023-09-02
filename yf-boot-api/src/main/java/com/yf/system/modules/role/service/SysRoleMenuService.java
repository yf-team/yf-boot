package com.yf.system.modules.role.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yf.system.modules.role.entity.SysRoleMenu;

import java.util.List;

/**
* <p>
* 角色菜单授权业务类
* </p>
*
* @author 聪明笨狗
* @since 2021-03-02 15:44
*/
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 查找角色授权
     * @param roleId
     * @return
     */
    List<String> findRoleMenus(String roleId);


    /**
     * 保存角色授权
     * @param roleId
     * @param ids
     */
    void saveRoleIds(String roleId, List<String> ids);
}
