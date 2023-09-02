package com.yf.system.modules.config.controller;

import com.yf.base.api.api.ApiRest;
import com.yf.base.api.api.controller.BaseController;
import com.yf.system.modules.config.service.CfgSwitchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
* <p>
* 通用配置控制器
* </p>
*
* @author 聪明笨狗
* @since 2020-04-17 09:12
*/
@Api(tags={"功能配置"})
@RestController
@RequestMapping("/api/sys/config/switch")
public class CfgSwitchController extends BaseController {

    @Autowired
    private CfgSwitchService baseService;


    /**
     * 保存功能开关
     * @param map
     * @return
     */
    @ApiOperation(value = "添加或修改")
    @RequestMapping(value = "/save", method = { RequestMethod.POST})
    public ApiRest save(@RequestBody Map<String,Object> map) {
        baseService.save(map);
        return super.success();
    }

    /**
     * 查找配置详情
     * @return
     */
    @ApiOperation(value = "查找详情")
    @RequestMapping(value = "/detail", method = { RequestMethod.POST})
    public ApiRest detail() {
        Map<String,Object> map = baseService.allMap();
        return super.success(map);
    }
}
