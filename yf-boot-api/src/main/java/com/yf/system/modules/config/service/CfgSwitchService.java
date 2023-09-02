package com.yf.system.modules.config.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yf.system.modules.config.entity.CfgSwitch;

import java.util.Map;

/**
* <p>
* 功能配置业务接口类
* </p>
*
* @author 聪明笨狗
* @since 2021-11-06 12:02
*/
public interface CfgSwitchService extends IService<CfgSwitch> {

    /**
     * 查找全部组成一个Map
     * @return
     */
    Map<String,Object> allMap();

    /**
     * 查询是否开启
     * @param id
     * @return
     */
    Boolean isOn(String id);

    /**
     * 取值
     * @param id
     * @return
     */
    String val(String id);

    /**
     * 保存功能开关
     * @param map
     */
    void save(Map<String,Object> map);
}
