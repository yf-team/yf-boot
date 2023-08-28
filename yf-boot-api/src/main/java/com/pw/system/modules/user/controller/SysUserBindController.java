package com.pw.system.modules.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pw.base.api.api.ApiRest;
import com.pw.base.api.api.controller.BaseController;
import com.pw.base.api.api.dto.PagingReqDTO;
import com.pw.system.modules.user.dto.SysUserBindDTO;
import com.pw.system.modules.user.service.SysUserBindService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 登录绑定控制器
* </p>
*
* @author 聪明笨狗
* @since 2021-08-02 14:49
*/
@Api(tags={"登录绑定"})
@RestController
@RequestMapping("/api/sys/user/bind")
public class SysUserBindController extends BaseController {

    @Autowired
    private SysUserBindService baseService;



    /**
    * 绑定列表
    * @param reqDTO
    * @return
    */
    @ApiOperation(value = "绑定列表")
    @RequestMapping(value = "/paging", method = { RequestMethod.POST})
    public ApiRest<IPage<SysUserBindDTO>> paging(@RequestBody PagingReqDTO<SysUserBindDTO> reqDTO) {

        //分页查询并转换
        IPage<SysUserBindDTO> page = baseService.paging(reqDTO);

        return super.success(page);
    }

    /**
     * 绑定列表
     * @param reqDTO
     * @return
     */
    @ApiOperation(value = "绑定列表", notes = "前端查询绑定列表")
    @RequestMapping(value = "/bind-list", method = { RequestMethod.POST})
    public ApiRest<IPage<SysUserBindDTO>> bindList(@RequestBody PagingReqDTO<SysUserBindDTO> reqDTO) {

        //分页查询并转换
        IPage<SysUserBindDTO> page = baseService.paging(reqDTO);

        return super.success(page);
    }
}
