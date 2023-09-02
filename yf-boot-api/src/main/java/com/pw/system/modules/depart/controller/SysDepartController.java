package com.pw.system.modules.depart.controller;

import com.pw.base.api.api.ApiRest;
import com.pw.base.api.api.controller.BaseController;
import com.pw.base.api.api.dto.BaseIdReqDTO;
import com.pw.base.api.api.dto.BaseIdsReqDTO;
import com.pw.base.utils.BeanMapper;
import com.pw.system.modules.depart.dto.SysDepartDTO;
import com.pw.system.modules.depart.dto.request.DepartSortReqDTO;
import com.pw.system.modules.depart.dto.response.SysDepartTreeDTO;
import com.pw.system.modules.depart.entity.SysDepart;
import com.pw.system.modules.depart.service.SysDepartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
* <p>
* 部门信息控制器
* </p>
*
* @author 聪明笨狗
* @since 2020-09-02 17:25
*/
@Api(tags={"部门信息"})
@RestController
@RequestMapping("/api/sys/depart")
public class SysDepartController extends BaseController {

    @Autowired
    private SysDepartService baseService;

    /**
    * 添加或修改
    * @param reqDTO
    * @return
    */
    @RequiresPermissions(value = {"sys:depart:add", "sys:depart:edit"}, logical = Logical.OR)
    @ApiOperation(value = "添加或修改")
    @PostMapping("/save")
    public ApiRest save(@RequestBody SysDepartDTO reqDTO) {
        baseService.save(reqDTO);
        return super.success();
    }

    /**
    * 批量删除
    * @param reqDTO
    * @return
    */
    @RequiresPermissions(value = {"sys:depart:delete"})
    @ApiOperation(value = "批量删除")
    @PostMapping("/delete")
    public ApiRest edit(@RequestBody BaseIdsReqDTO reqDTO) {
        //根据ID删除
        baseService.delete(reqDTO.getIds());
        return super.success();
    }

    /**
    * 查找详情
    * @param reqDTO
    * @return
    */
    @RequiresPermissions(value = {"sys:depart:list"})
    @ApiOperation(value = "查找详情")
    @PostMapping("/detail")
    public ApiRest<SysDepartDTO> find(@RequestBody BaseIdReqDTO reqDTO) {
        SysDepart entity = baseService.getById(reqDTO.getId());
        SysDepartDTO dto = new SysDepartDTO();
        BeanMapper.copy(entity, dto);
        return super.success(dto);
    }


    /**
     * 树列表
     * @return
     */
    @ApiOperation(value = "部门树列表")
    @PostMapping("/tree")
    public ApiRest<List<SysDepartTreeDTO>> tree() {
        List<SysDepartTreeDTO> dtoList = baseService.findTree(true);
        return super.success(dtoList);
    }


    /**
     * 部门树-注册用
     * @return
     */
    @ApiOperation(value = "部门树-用于用户注册", notes = "部门树权限为公开的，需求登录即可访问")
    @PostMapping( "/tree-select")
    public ApiRest<List<SysDepartTreeDTO>> treeSelect() {
        List<SysDepartTreeDTO> dtoList = baseService.findTree(false);
        return super.success(dtoList);
    }

    /**
     * 分类排序
     * @param reqDTO
     * @return
     */
    @RequiresPermissions(value = {"sys:depart:edit"}, logical = Logical.OR)
    @ApiOperation(value = "调整部门排序")
    @PostMapping( "/sort")
    public ApiRest sort(@RequestBody DepartSortReqDTO reqDTO) {
        baseService.sort(reqDTO);
        return super.success();
    }
}
