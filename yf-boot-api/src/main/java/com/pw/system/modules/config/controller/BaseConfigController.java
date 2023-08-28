package com.pw.system.modules.config.controller;

import com.pw.base.api.api.ApiRest;
import com.pw.base.api.api.controller.BaseController;
import com.pw.base.api.utils.BeanMapper;
import com.pw.system.modules.config.dto.CfgBaseDTO;
import com.pw.system.modules.config.service.CfgBaseService;
import com.pw.system.modules.config.service.CfgSwitchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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
@RequestMapping("/api/sys/config")
public class BaseConfigController extends BaseController {

    @Autowired
    private CfgBaseService baseService;

    @Autowired
    private CfgSwitchService cfgSwitchService;

    /**
    * 添加或修改
    * @param reqDTO
    * @return
    */
    @ApiOperation(value = "保存基础配置")
    @RequestMapping(value = "/save", method = { RequestMethod.POST})
    public ApiRest save(@RequestBody CfgBaseDTO reqDTO) {
        baseService.save(reqDTO);
        return super.success();
    }

    /**
    * 查找详情
    * @return
    */
    @ApiOperation(value = "简略详情")
    @RequestMapping(value = "/detail", method = { RequestMethod.POST})
    public ApiRest<Map<String,Object>> detail() {
        CfgBaseDTO dto = baseService.findSimple();

        // 返回数据
        Map<String,Object> resMap = new HashMap<>();

        // 网站设置
        BeanMapper.copy(dto, resMap);

        // 功能开关
        Map<String,Object> sMap = cfgSwitchService.allMap();
        resMap.putAll(sMap);
        resMap.put("props", sMap);
        return super.success(resMap);
    }
}
