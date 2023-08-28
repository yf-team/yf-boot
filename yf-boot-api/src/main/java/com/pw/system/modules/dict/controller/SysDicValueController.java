package com.pw.system.modules.dict.controller;

import com.pw.base.api.api.ApiRest;
import com.pw.base.api.api.controller.BaseController;
import com.pw.base.api.api.dto.BaseIdRespDTO;
import com.pw.base.api.api.dto.BaseIdsReqDTO;
import com.pw.system.modules.dict.dto.SysDicValueDTO;
import com.pw.system.modules.dict.dto.ext.DicValueTreeDTO;
import com.pw.system.modules.dict.dto.request.SysDicValueReqDTO;
import com.pw.system.modules.dict.service.SysDicValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
* <p>
* 分类字典控制器
* </p>
*
* @author 聪明笨狗
* @since 2020-12-01 14:00
*/
@Api(tags={"字典值管理"})
@RestController
@RequestMapping("/api/sys/dic/value")
public class SysDicValueController extends BaseController {

    @Autowired
    private SysDicValueService baseService;

    /**
    * 添加或修改
    * @param reqDTO
    * @return
    */
    @RequiresPermissions(value = {"sys:dict:add", "sys:dict:edit"}, logical = Logical.OR)
    @ApiOperation(value = "添加或修改")
    @RequestMapping(value = "/save", method = { RequestMethod.POST})
    public ApiRest<BaseIdRespDTO> save(@RequestBody SysDicValueDTO reqDTO) {
        baseService.save(reqDTO);
        return super.success();
    }

    /**
    * 批量删除
    * @param reqDTO
    * @return
    */
    @RequiresPermissions(value = {"sys:dict:delete"})
    @ApiOperation(value = "批量删除")
    @RequestMapping(value = "/delete", method = { RequestMethod.POST})
    public ApiRest edit(@RequestBody BaseIdsReqDTO reqDTO) {
        //根据ID删除
        baseService.removeByIds(reqDTO.getIds());
        return super.success();
    }

    /**
     * 分类树列表
     * @return
     */
    @ApiOperation(value = "分类树列表")
    @RequestMapping(value = "/tree", method = { RequestMethod.POST})
    public ApiRest<List<DicValueTreeDTO>> tree(@RequestBody SysDicValueReqDTO reqDTO) {
        List<DicValueTreeDTO> dtoList = baseService.findTree(reqDTO);
        return super.success(dtoList);
    }

}
