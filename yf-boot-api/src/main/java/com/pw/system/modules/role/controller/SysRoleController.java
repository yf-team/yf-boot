package com.pw.system.modules.role.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pw.base.api.annon.DataProtect;
import com.pw.base.api.api.ApiRest;
import com.pw.base.api.api.controller.BaseController;
import com.pw.base.api.api.dto.BaseIdReqDTO;
import com.pw.base.api.api.dto.BaseIdsReqDTO;
import com.pw.base.api.api.dto.PagingReqDTO;
import com.pw.base.utils.BeanMapper;
import com.pw.system.modules.role.dto.SysRoleDTO;
import com.pw.system.modules.role.entity.SysRole;
import com.pw.system.modules.role.service.SysRoleMenuService;
import com.pw.system.modules.role.service.SysRoleService;
import com.pw.system.modules.user.dto.request.SysRoleMenuReqDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 管理用户控制器
 * </p>
 *
 * @author 聪明笨狗
 * @since 2020-04-13 16:57
 */
@Api(tags = {"角色管理"})
@RestController
@RequestMapping("/api/sys/role")
public class SysRoleController extends BaseController {

    @Autowired
    private SysRoleService baseService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;


    /**
     * 添加或修改
     * @param reqDTO
     * @return
     */
    @RequiresPermissions(value = {"sys:role:add", "sys:role:update"}, logical = Logical.OR)
    @DataProtect(clazz = SysRole.class, update = true)
    @ApiOperation(value = "添加或修改")
    @PostMapping("/save")
    public ApiRest save(@Validated @RequestBody SysRoleDTO reqDTO) {
        baseService.save(reqDTO);
        return super.success();
    }

    /**
     * 批量删除
     * @param reqDTO
     * @return
     */
    @RequiresPermissions(value = {"sys:role:delete"})
    @DataProtect(clazz = SysRole.class, delete = true)
    @ApiOperation(value = "批量删除")
    @PostMapping("/delete")
    public ApiRest delete(@RequestBody BaseIdsReqDTO reqDTO) {
        //根据ID删除
        baseService.delete(reqDTO.getIds());
        return super.success();
    }


    /**
     * 查找详情
     * @param reqDTO
     * @return
     */
    @RequiresPermissions(value = {"sys:role:add", "sys:role:update"}, logical = Logical.OR)
    @ApiOperation(value = "查找详情")
    @PostMapping("/detail")
    public ApiRest<SysRoleDTO> detail(@RequestBody BaseIdReqDTO reqDTO) {
        SysRole entity = baseService.getById(reqDTO.getId());
        SysRoleDTO dto = new SysRoleDTO();
        BeanMapper.copy(entity, dto);
        return super.success(dto);
    }


    /**
     * 分页查找
     * @param reqDTO
     * @return
     */
    @RequiresPermissions(value = {"sys:role:paging"})
    @ApiOperation(value = "分页查找")
    @PostMapping("/paging")
    public ApiRest<IPage<SysRoleDTO>> paging(@RequestBody PagingReqDTO<SysRoleDTO> reqDTO) {

        //分页查询并转换
        IPage<SysRoleDTO> page = baseService.paging(reqDTO);
        return super.success(page);
    }

    /**
     * 查找角色菜单授权
     * @return
     */
    @RequiresPermissions(value = {"sys:role:grant"})
    @ApiOperation(value = "查找角色菜单授权")
    @PostMapping("/list-menus")
    public ApiRest<List<String>> listMenus(@RequestBody BaseIdReqDTO reqDTO) {
        //分页查询并转换
        List<String> ids = sysRoleMenuService.findRoleMenus(reqDTO.getId());
        return super.success(ids);
    }


    /**
     * 保存角色菜单授权
     * @return
     */
    @DataProtect(clazz = SysRole.class, update = true)
    @RequiresPermissions(value = {"sys:role:grant"})
    @ApiOperation(value = "保存角色菜单授权")
    @PostMapping("/save-menus")
    public ApiRest saveMenus(@RequestBody SysRoleMenuReqDTO reqDTO) {

        // 保存授权
        sysRoleMenuService.saveRoleIds(reqDTO.getId(), reqDTO.getMenuIds());
        return super.success();
    }


}
