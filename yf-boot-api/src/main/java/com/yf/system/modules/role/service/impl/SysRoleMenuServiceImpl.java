package com.yf.system.modules.role.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yf.ability.shiro.MyShiroRealm;
import com.yf.base.utils.CacheKey;
import com.yf.system.modules.role.entity.SysRole;
import com.yf.system.modules.role.entity.SysRoleMenu;
import com.yf.system.modules.role.mapper.SysRoleMenuMapper;
import com.yf.system.modules.role.service.SysRoleMenuService;
import com.yf.system.modules.role.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
* <p>
* 角色菜单授权业务实现类
* </p>
*
* @author 聪明笨狗
* @since 2021-03-02 15:44
*/
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Autowired
    private MyShiroRealm myShiroRealm;

    @Autowired
    private SysRoleService roleService;

    /**
     * 默认后台首页
     */
    private static final String [] DASH_MENUS = {"1367010529427607568", "1367010529427607569"};

    @Override
    public List<String> findRoleMenus(String roleId) {
        //查询条件
        QueryWrapper<SysRoleMenu> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysRoleMenu::getRoleId, roleId);
        List<SysRoleMenu> list = this.list(wrapper);

        List<String> ids = new ArrayList<>();
        if(!CollectionUtils.isEmpty(list)){
            for (SysRoleMenu item: list){
                ids.add(item.getMenuId());
            }
        }
        return ids;
    }


    /**
     * 保存授权
     * @param roleId
     * @param ids
     */
    @CacheEvict(value = CacheKey.MENU, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveRoleIds(String roleId, List<String> ids) {

        // 查找角色信息
        SysRole role = roleService.getById(roleId);

        // 先清理
        QueryWrapper<SysRoleMenu> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysRoleMenu::getRoleId, roleId);
        this.remove(wrapper);

        // 再保存
        if(!CollectionUtils.isEmpty(ids)){
            List<SysRoleMenu> list = new ArrayList<>();
            for (String id: ids){
                SysRoleMenu item = new SysRoleMenu();
                item.setMenuId(id);
                item.setRoleId(roleId);
                list.add(item);
            }
            this.saveBatch(list);
        }

        // 直接清理掉全部缓存
        myShiroRealm.getAuthorizationCache().clear();
    }
}
