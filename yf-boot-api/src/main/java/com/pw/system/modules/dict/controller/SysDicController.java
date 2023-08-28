package com.pw.system.modules.dict.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pw.base.api.annon.DataProtect;
import com.pw.base.api.api.ApiRest;
import com.pw.base.api.api.controller.BaseController;
import com.pw.base.api.api.dto.BaseIdsReqDTO;
import com.pw.base.api.api.dto.PagingReqDTO;
import com.pw.system.modules.dict.dto.SysDicDTO;
import com.pw.system.modules.dict.entity.SysDic;
import com.pw.system.modules.dict.service.SysDicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 分类字典控制器
* </p>
*
* @author 聪明笨狗
* @since 2020-12-01 14:00
*/
@Api(tags={"字典项管理"}, consumes = "数据字典项管理，也就是一级目录")
@RestController
@RequestMapping("/api/sys/dic")
public class SysDicController extends BaseController {

    @Autowired
    private SysDicService baseService;


    /**
    * 添加或修改
    * @param reqDTO
    * @return
    */
    @RequiresPermissions(value = {"sys:dict:add", "sys:dict:edit"}, logical = Logical.OR)
    @DataProtect(clazz = SysDic.class, update = true)
    @ApiOperation(value = "添加或修改")
    @RequestMapping(value = "/save", method = { RequestMethod.POST})
    public ApiRest save(@RequestBody SysDicDTO reqDTO) {
        baseService.save(reqDTO);
        return super.success();
    }

    /**
    * 批量删除
    * @param reqDTO
    * @return
    */
    @RequiresPermissions(value = {"sys:dict:delete"})
    @DataProtect(clazz = SysDic.class, delete = true)
    @ApiOperation(value = "批量删除")
    @RequestMapping(value = "/delete", method = { RequestMethod.POST})
    public ApiRest delete(@RequestBody BaseIdsReqDTO reqDTO) {
        //根据ID删除
        baseService.delete(reqDTO.getIds());
        return super.success();
    }

    /**
    * 分页查找
    * @param reqDTO
    * @return
    */
    @ApiOperation(value = "分页查找")
    @RequestMapping(value = "/paging", method = { RequestMethod.POST})
    public ApiRest<IPage<SysDicDTO>> paging(@RequestBody PagingReqDTO<SysDicDTO> reqDTO) {
        //分页查询并转换
        IPage<SysDicDTO> page = baseService.paging(reqDTO);
        return super.success(page);
    }

}
