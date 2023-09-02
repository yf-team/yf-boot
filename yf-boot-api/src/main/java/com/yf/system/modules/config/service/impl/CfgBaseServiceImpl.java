package com.yf.system.modules.config.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yf.base.utils.BeanMapper;
import com.yf.system.modules.config.dto.CfgBaseDTO;
import com.yf.system.modules.config.entity.CfgBase;
import com.yf.system.modules.config.mapper.CfgBaseMapper;
import com.yf.system.modules.config.service.CfgBaseService;
import com.yf.base.utils.CacheKey;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* <p>
* 语言设置 服务实现类
* </p>
*
* @author 聪明笨狗
* @since 2020-04-17 09:12
*/
@Service
public class CfgBaseServiceImpl extends ServiceImpl<CfgBaseMapper, CfgBase> implements CfgBaseService {


    @Cacheable(value = CacheKey.SITE, key = "'1'")
    @Override
    public CfgBaseDTO findSimple() {

        QueryWrapper<CfgBase> wrapper = new QueryWrapper<>();
        wrapper.last(" LIMIT 1");

        CfgBase entity = this.getOne(wrapper, false);
        CfgBaseDTO dto = new CfgBaseDTO();
        BeanMapper.copy(entity, dto);

        return dto;
    }

    @CacheEvict(value = CacheKey.SITE, allEntries=true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(CfgBaseDTO reqDTO) {
        //复制参数
        CfgBase entity = new CfgBase();
        BeanMapper.copy(reqDTO, entity);
        this.saveOrUpdate(entity);
    }

}
