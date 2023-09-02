package com.yf.system.modules.config.controller;

import com.yf.base.api.api.ApiRest;
import com.yf.base.api.api.controller.BaseController;
import com.yf.system.modules.config.dto.CfgUploadDTO;
import com.yf.system.modules.config.service.CfgUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 通用配置控制器
* </p>
*
* @author 聪明笨狗
* @since 2020-04-17 09:12
*/
@Api(tags={"通用配置"})
@RestController
@RequestMapping("/api/sys/config/upload")
public class CfgUploadController extends BaseController {

    @Autowired
    private CfgUploadService baseService;


    /**
     * 添加或修改
     * @param reqDTO
     * @return
     */
    @ApiOperation(value = "添加或修改")
    @RequestMapping(value = "/save", method = { RequestMethod.POST})
    public ApiRest save(@RequestBody CfgUploadDTO reqDTO) {
        baseService.save(reqDTO);
        return super.success();
    }

    /**
     * 查找详情
     * @param reqDTO
     * @return
     */
    @ApiOperation(value = "查找详情")
    @RequestMapping(value = "/detail", method = { RequestMethod.POST})
    public ApiRest<CfgUploadDTO> detail(@RequestBody CfgUploadDTO reqDTO) {
        CfgUploadDTO dto = baseService.detail(reqDTO.getProvider());
        return super.success(dto);
    }
}
