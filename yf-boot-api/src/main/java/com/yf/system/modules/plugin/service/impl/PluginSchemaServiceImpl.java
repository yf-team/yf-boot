package com.yf.system.modules.plugin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.yf.base.api.api.dto.PagingReqDTO;
import com.yf.base.utils.BeanMapper;
import com.yf.base.utils.jackson.JsonHelper;
import com.yf.system.modules.plugin.dto.PluginSchemaDTO;
import com.yf.system.modules.plugin.entity.PluginSchema;
import com.yf.system.modules.plugin.mapper.PluginSchemaMapper;
import com.yf.system.modules.plugin.service.PluginSchemaService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <p>
* 插件元数据业务实现类
* </p>
*
* @author 聪明笨狗
* @since 2022-09-05 10:05
*/
@Service
public class PluginSchemaServiceImpl extends ServiceImpl<PluginSchemaMapper, PluginSchema> implements PluginSchemaService {

    @Override
    public IPage<PluginSchemaDTO> paging(PagingReqDTO<PluginSchemaDTO> reqDTO) {

        //查询条件
        QueryWrapper<PluginSchema> wrapper = new QueryWrapper<>();

        // 请求参数
        PluginSchemaDTO params = reqDTO.getParams();

        //获得数据
        IPage<PluginSchema> page = this.page(reqDTO.toPage(), wrapper);
        //转换结果
        IPage<PluginSchemaDTO> pageData = JsonHelper.parseObject(page, new TypeReference<Page<PluginSchemaDTO>>(){});
        return pageData;
    }


    @Override
    public void save(PluginSchemaDTO reqDTO){
        //复制参数
        PluginSchema entity = new PluginSchema();
        BeanMapper.copy(reqDTO, entity);
        this.saveOrUpdate(entity);
    }

    @Override
    public void delete(List<String> ids){
        //批量删除
        this.removeByIds(ids);
    }

    @Override
    public PluginSchemaDTO detail(String id){
        PluginSchema entity = this.getById(id);
        PluginSchemaDTO dto = new PluginSchemaDTO();
        BeanMapper.copy(entity, dto);
        return dto;
    }

    @Override
    public List<PluginSchemaDTO> list(PluginSchemaDTO reqDTO){

        //分页查询并转换
        QueryWrapper<PluginSchema> wrapper = new QueryWrapper<>();

        //转换并返回
        List<PluginSchema> list = this.list(wrapper);

        //转换数据
        List<PluginSchemaDTO> dtoList = BeanMapper.mapList(list, PluginSchemaDTO.class);

        return dtoList;
    }
}
