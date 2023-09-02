package com.pw.system.modules.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pw.ability.shiro.dto.SysUserLoginDTO;
import com.pw.base.api.annon.DataProtect;
import com.pw.base.api.api.ApiRest;
import com.pw.base.api.api.controller.BaseController;
import com.pw.base.api.api.dto.*;
import com.pw.base.api.exception.ServiceException;
import com.pw.system.modules.user.dto.request.*;
import com.pw.system.modules.user.dto.response.UserListRespDTO;
import com.pw.system.modules.user.entity.SysUser;
import com.pw.system.modules.user.enums.UserState;
import com.pw.system.modules.user.service.SysUserRoleService;
import com.pw.system.modules.user.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 管理用户控制器
 * </p>
 *
 * @author 聪明笨狗
 * @since 2020-04-13 16:57
 */
@Api(tags = {"用户管理"})
@RestController
@Log4j2
@RequestMapping("/api/sys/user")
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService baseService;

    @Autowired
    private SysUserRoleService sysUserRoleService;


    /**
     * 用户详情
     *
     * @return
     */
    @RequiresPermissions(value = {"sys:user:add", "sys:user:edit"}, logical = Logical.OR)
    @ApiOperation(value = "用户详情")
    @RequestMapping(value = "/detail", method = {RequestMethod.POST})
    public ApiRest<SysUserSaveReqDTO> detail(@RequestBody BaseIdReqDTO reqDTO) {
        SysUserSaveReqDTO respDTO = baseService.detail(reqDTO.getId());
        return super.success(respDTO);
    }

    /**
     * 用户登录
     *
     * @return
     */
    @ApiOperation(value = "账号密码登录")
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public ApiRest<SysUserLoginDTO> login(@RequestBody SysUserLoginReqDTO reqDTO) {
        SysUserLoginDTO respDTO = baseService.login(reqDTO);
        return super.success(respDTO);
    }




    /**
     * 用户登录
     *
     * @return
     */
    @ApiOperation(value = "退出登录")
    @RequestMapping(value = "/logout", method = {RequestMethod.POST})
    public ApiRest logout(HttpServletRequest request) {
        String token = request.getHeader("token");
        baseService.logout(token);
        return super.success();
    }

    /**
     * 获取会话
     *
     * @return
     */
    @ApiOperation(value = "获取会话")
    @RequestMapping(value = "/info", method = {RequestMethod.POST})
    public ApiRest info(@RequestBody BaseTokenReqDTO reqDTO) {
        SysUserLoginDTO respDTO = baseService.token(reqDTO.getToken());
        return success(respDTO);
    }

    /**
     * 修改用户资料
     *
     * @return
     */
    @DataProtect(clazz = SysUser.class, update = true, currUsr = true)
    @ApiOperation(value = "修改用户资料")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public ApiRest update(@RequestBody SysUserUpdateReqDTO reqDTO) {
        baseService.update(reqDTO);
        return success();
    }




    /**
     * 保存或修改系统用户
     *
     * @return
     */
    @RequiresPermissions(value = {"sys:user:add", "sys:user:edit"}, logical = Logical.OR)
    @DataProtect(clazz = SysUser.class, update = true)
    @ApiOperation(value = "保存或修改")
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public ApiRest save(@RequestBody SysUserSaveReqDTO reqDTO) {
        baseService.save(reqDTO);
        return success();
    }


    /**
     * 批量删除
     *
     * @param reqDTO
     * @return
     */
    @RequiresPermissions(value = {"sys:user:delete"})
    @DataProtect(clazz = SysUser.class, delete = true)
    @ApiOperation(value = "批量删除")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public ApiRest delete(@RequestBody BaseIdsReqDTO reqDTO) {
        //根据ID删除
        baseService.delete(reqDTO.getIds());
        return super.success();
    }

    /**
     * 分页查找
     *
     * @param reqDTO
     * @return
     */
    @RequiresPermissions(value = {"sys:user:paging"})
    @ApiOperation(value = "分页查找")
    @RequestMapping(value = "/paging", method = {RequestMethod.POST})
    public ApiRest<IPage<UserListRespDTO>> paging(@RequestBody PagingReqDTO<SysUserQueryReqDTO> reqDTO) {

        //分页查询并转换
        IPage<UserListRespDTO> page = baseService.paging(reqDTO);
        return super.success(page);
    }

    /**
     * 修改状态
     *
     * @param reqDTO
     * @return
     */
    @RequiresPermissions(value = {"sys:user:state"})
    @ApiOperation(value = "修改状态")
    @RequestMapping(value = "/state", method = {RequestMethod.POST})
    public ApiRest state(@RequestBody BaseStateReqDTO reqDTO) {

        // 条件
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .in(SysUser::getId, reqDTO.getIds())
                .ne(SysUser::getUserName, "admin");

        SysUser record = new SysUser();
        record.setState(reqDTO.getState());
        baseService.update(record, wrapper);
        return super.success();
    }

    /**
     * 用户注册
     * @return
     */
    @ApiOperation(value = "用户注册", notes = "通过账号密码注册")
    @RequestMapping(value = "/reg", method = {RequestMethod.POST})
    public ApiRest<SysUserLoginDTO> reg(@RequestBody UserRegReqDTO reqDTO) {
        SysUserLoginDTO respDTO = baseService.reg(reqDTO);

        // 待审核的状态
        if(UserState.AUDIT.equals(respDTO.getState())){
            throw new ServiceException("注册成功，管理员审核后方可登录！");
        }
        return success(respDTO);
    }


    /**
     * 批量修改角色
     *
     * @return
     */
    @RequiresPermissions(value = {"sys:user:batch-role"})
    @ApiOperation(value = "批量修改角色")
    @RequestMapping(value = "/batch-role", method = {RequestMethod.POST})
    public ApiRest batchRole(@RequestBody UserRoleReqDTO reqDTO) {
        sysUserRoleService.batchRole(reqDTO);
        return success();
    }

}
