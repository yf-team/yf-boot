package com.yf.system.modules.menu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yf.base.api.exception.ServiceException;
import com.yf.base.utils.BeanMapper;
import com.yf.base.utils.CacheKey;
import com.yf.base.utils.jackson.JsonHelper;
import com.yf.system.modules.depart.dto.request.DepartSortReqDTO;
import com.yf.system.modules.menu.dto.SysMenuDTO;
import com.yf.system.modules.menu.dto.response.MenuTreeRespDTO;
import com.yf.system.modules.menu.dto.response.RouteRespDTO;
import com.yf.system.modules.menu.entity.SysMenu;
import com.yf.system.modules.menu.eums.MenuType;
import com.yf.system.modules.menu.mapper.SysMenuMapper;
import com.yf.system.modules.menu.service.SysMenuService;
import com.yf.system.modules.role.entity.SysRoleMenu;
import com.yf.system.modules.role.service.SysRoleMenuService;
import com.yf.system.modules.user.UserUtils;
import com.yf.system.modules.user.entity.SysUserRole;
import com.yf.system.modules.user.service.SysUserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统菜单业务实现类
 * </p>
 *
 * @author 聪明笨狗
 * @since 2021-03-02 13:09
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {


    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    private SysUserRoleService sysUserRoleService;


    @CacheEvict(value = CacheKey.MENU, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(SysMenuDTO reqDTO) {

        // 添加模式
        boolean add = false;

        if (StringUtils.isBlank(reqDTO.getId())) {
            int sort = baseMapper.findMaxSort(reqDTO.getParentId());
            reqDTO.setSort(sort + 1);
            add = true;
        } else {
            reqDTO.setSort(null);
        }


        SysMenu entity = new SysMenu();
        BeanMapper.copy(reqDTO, entity);

        this.saveOrUpdate(entity);

        // 赋值给当前用户最大的角色
        if (add) {
            SysUserRole sr = sysUserRoleService.findMaxRole(UserUtils.getUserId());
            if (sr != null) {
                SysRoleMenu rm = new SysRoleMenu();
                rm.setRoleId(sr.getRoleId());
                rm.setMenuId(entity.getId());
                sysRoleMenuService.save(rm);
            }
        }
    }

    @CacheEvict(value = CacheKey.MENU, allEntries = true)
    @Override
    public void delete(List<String> ids) {
        //查询条件
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(SysMenu::getParentId, ids);
        long count = this.count(wrapper);
        if (count > 0) {
            throw new ServiceException("请先删除下级菜单！");
        }

        this.removeByIds(ids);
    }


    @Cacheable(value = CacheKey.MENU, key = "'routes-'+#p0")
    @Override
    public List<RouteRespDTO> listMenuByRoles(List<String> roleList) {

        // 空权限了
        if (CollectionUtils.isEmpty(roleList)) {
            throw new ServiceException("您还没有任何角色授权，请联系管理员！");
        }

        System.out.println("++++++++++角色列表：" + JsonHelper.toJson(roleList));

        // 获取路由表
        List<RouteRespDTO> routes = baseMapper.listMenuByRoles(roleList);

        // 构建
        Map<String, List<RouteRespDTO>> childMap = new HashMap<>(16);
        for (RouteRespDTO item : routes) {
            String parentId = item.getParentId();
            List<RouteRespDTO> list = null;
            if (childMap.containsKey(parentId)) {
                list = childMap.get(parentId);
            } else {
                list = new ArrayList<>();
            }
            // 更新Map
            list.add(item);
            childMap.put(parentId, list);
        }


        // 获取顶级的
        List<RouteRespDTO> topList = childMap.get("0");
        if (!CollectionUtils.isEmpty(topList)) {
            for (RouteRespDTO item : topList) {
                this.fillChildren(childMap, item);
            }
        }

        return topList;
    }

    @Cacheable(value = CacheKey.MENU, key = "'permissions-'+#p0")
    @Override
    public List<String> listPermissionByRoles(List<String> roleIds) {
        System.out.println("+++++roles:" + JsonHelper.toJson(roleIds));
        return baseMapper.listPermissionByRoles(roleIds);
    }


    @Override
    public List<MenuTreeRespDTO> listTree() {
        // 列出用户菜单
        List<MenuTreeRespDTO> list = baseMapper.listTree();
        return list;
    }


    @CacheEvict(value = CacheKey.MENU, allEntries = true)
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sort(DepartSortReqDTO reqDTO) {

        SysMenu form = this.getById(reqDTO.getForm());
        SysMenu to = this.getById(reqDTO.getTo());


        // 跨级别拖拽
        if (!form.getParentId().equals(to.getParentId()) || (to.getParentId().equals("0") &&  form.getParentId().equals("0"))) {

            // 只允许：目录-->目录 | 菜单-->目录 | 菜单-->菜单(旁边)
            if (!(form.getMenuType().equals(MenuType.DIR) && to.getMenuType().equals(MenuType.DIR))
                    && !(form.getMenuType().equals(MenuType.MENU) && to.getMenuType().equals(MenuType.DIR))
                    && !(form.getMenuType().equals(MenuType.MENU) && to.getMenuType().equals(MenuType.MENU) && !"inner".equals(reqDTO.getDropType()))
            ) {
                throw new ServiceException("啊啊啊，太乱了，不能这样拽！");
            }


            int sort = 0;

            // 放到后面
            if ("after".equals(reqDTO.getDropType())) {
                sort = to.getSort() + 1;
                // 同父级
                form.setParentId(to.getParentId());
                // 后面的排序增大
                baseMapper.incrSort(to.getParentId(), sort);
            }

            // 放到前面
            if ("before".equals(reqDTO.getDropType())) {
                sort = to.getSort();
                // 同父级
                form.setParentId(to.getParentId());
                // 后面的排序增大
                baseMapper.incrSort(to.getParentId(), sort);
            }

            // 放入到最后
            if ("inner".equals(reqDTO.getDropType())) {
                sort = baseMapper.findMaxSort(to.getId());
                sort += 1;
                form.setParentId(to.getId());
            }

            form.setSort(sort);
            this.updateById(form);

        } else {
            // 同级别拖拽，直接交换排序
            if (!"inner".equals(reqDTO.getDropType())) {
                int formSort = form.getSort();
                int toSort = to.getSort();
                form.setSort(toSort);
                to.setSort(formSort);
                this.updateById(form);
                this.updateById(to);
            }
        }
    }


    /**
     * 递归去做填充数据
     *
     * @param map
     * @param item
     */
    private void fillChildren(Map<String, List<RouteRespDTO>> map, RouteRespDTO item) {

        // 隐藏属性无需出现
        if(item.getHidden()!=null && !item.getHidden()){
            item.setHidden(null);
        }

        //设置子类
        if (map.containsKey(item.getId())) {

            List<RouteRespDTO> children = map.get(item.getId());

            if (!CollectionUtils.isEmpty(children)) {
                for (RouteRespDTO sub : children) {
                    this.fillChildren(map, sub);
                }
            }
            item.setChildren(children);
        }
    }
}
