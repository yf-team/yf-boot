package com.yf.system.modules.config.controller;

import com.yf.base.api.api.ApiRest;
import com.yf.base.api.api.controller.BaseController;
import com.yf.base.utils.BeanMapper;
import com.yf.system.modules.config.dto.CfgBaseDTO;
import com.yf.system.modules.config.service.CfgBaseService;
import com.yf.system.modules.config.service.CfgSwitchService;
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
public class CfgBaseController extends BaseController {

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

        // 返回数据
        Map<String,Object> resMap = new HashMap<>(16);

        // 网站设置
        CfgBaseDTO dto = baseService.findSimple();
        BeanMapper.copy(dto, resMap);

        // 功能开关封装到props属性中
        Map<String,Object> props = cfgSwitchService.allMap();
        resMap.putAll(props);
        resMap.put("props", props);
        return super.success(resMap);
    }
}
