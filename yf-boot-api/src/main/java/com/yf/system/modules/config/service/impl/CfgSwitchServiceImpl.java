package com.yf.system.modules.config.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yf.system.modules.config.entity.CfgSwitch;
import com.yf.system.modules.config.mapper.CfgSwitchMapper;
import com.yf.system.modules.config.service.CfgSwitchService;
import com.yf.base.utils.CacheKey;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* <p>
* 功能配置业务实现类
* </p>
*
* @author 聪明笨狗
* @since 2021-11-06 12:02
*/
@Service
public class CfgSwitchServiceImpl extends ServiceImpl<CfgSwitchMapper, CfgSwitch> implements CfgSwitchService {


    @Cacheable(value = CacheKey.SWITCH, key = "'all'")
    @Override
    public Map<String, Object> allMap() {

        // 返回结果
        Map<String,Object> map = new HashMap<>();

        List<CfgSwitch> list = this.list();
        if(!CollectionUtils.isEmpty(list)){
            for(CfgSwitch item: list){

                String val = item.getVal();
                if("true".equals(val) || "false".equals(val)){
                    map.put(item.getId(), Boolean.valueOf(val));
                    continue;
                }

                if("1".equals(val) || "0".equals(val)){
                    map.put(item.getId(), Integer.parseInt(val));
                    continue;
                }

                map.put(item.getId(), val);
            }
        }

        return map;
    }

    @Cacheable(value = CacheKey.SWITCH, key = "'on:'+#id")
    @Override
    public Boolean isOn(String id) {
        CfgSwitch cfgSwitch = this.getById(id);
        return cfgSwitch!=null && !StringUtils.isEmpty(cfgSwitch.getVal()) &&
                ("1".equals(cfgSwitch.getVal()) || "true".equals(cfgSwitch.getVal()));
    }

    @Cacheable(value = CacheKey.SWITCH, key = "'val:'+#id")
    @Override
    public String val(String id) {
        CfgSwitch cfgSwitch = this.getById(id);
        if(cfgSwitch!=null){
            return cfgSwitch.getVal();
        }

        return "";
    }

    @CacheEvict(value = CacheKey.SWITCH, allEntries=true)
    @Override
    public void save(Map<String, Object> map) {

        if(map == null || map.isEmpty()){
            return;
        }

        List<CfgSwitch> list = this.list();
        for(String key: map.keySet()){
            CfgSwitch item = new CfgSwitch();
            item.setId(key);
            item.setVal(String.valueOf(map.get(key)));
            list.add(item);
        }
        this.saveOrUpdateBatch(list);
    }
}
