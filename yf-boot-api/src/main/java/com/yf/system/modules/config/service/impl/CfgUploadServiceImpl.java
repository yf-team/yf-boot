package com.yf.system.modules.config.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yf.base.utils.BeanMapper;
import com.yf.system.modules.config.dto.CfgUploadDTO;
import com.yf.system.modules.config.entity.CfgUpload;
import com.yf.system.modules.config.mapper.CfgUploadMapper;
import com.yf.system.modules.config.service.CfgUploadService;
import com.yf.base.utils.CacheKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* <p>
* 上传配置业务实现类
* </p>
*
* @author 聪明笨狗
* @since 2022-03-22 17:46
*/
@Service
public class CfgUploadServiceImpl extends ServiceImpl<CfgUploadMapper, CfgUpload> implements CfgUploadService {

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheKey.UPLOAD, allEntries=true)
    @Override
    public void save(CfgUploadDTO reqDTO) {
        // 保存
        this.clearEnabled();

        // 直接删除
        QueryWrapper<CfgUpload> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(CfgUpload::getProvider, reqDTO.getProvider());
        this.remove(wrapper);

        //复制参数
        CfgUpload entity = new CfgUpload();
        BeanMapper.copy(reqDTO, entity);
        entity.setId(IdWorker.getIdStr());
        entity.setEnabled(true);
        this.save(entity);

    }

    @Cacheable(value = CacheKey.UPLOAD, key = "#provider")
    @Override
    public CfgUploadDTO detail(String provider) {

        // 查找默认的
        if (StringUtils.isBlank(provider)) {
            //分页查询并转换
            QueryWrapper<CfgUpload> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(CfgUpload::getEnabled, true);

            CfgUpload entity = this.getOne(wrapper, false);
            CfgUploadDTO dto = new CfgUploadDTO();
            BeanMapper.copy(entity, dto);
            return dto;

        } else {
            QueryWrapper<CfgUpload> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(CfgUpload::getProvider, provider);
            CfgUpload entity = this.getOne(wrapper, false);
            CfgUploadDTO dto = new CfgUploadDTO();
            BeanMapper.copy(entity, dto);
            return dto;
        }
    }

    /**
     * 清除使用状态
     */
    private void clearEnabled() {
        QueryWrapper<CfgUpload> wrapper = new QueryWrapper<>();
        CfgUpload entity = new CfgUpload();
        entity.setEnabled(false);
        this.update(entity, wrapper);
    }
}
