package com.yf.system.modules.plugin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.yf.base.api.api.dto.PagingReqDTO;
import com.yf.base.utils.BeanMapper;
import com.yf.base.utils.jackson.JsonHelper;
import com.yf.system.modules.plugin.dto.PluginGroupDTO;
import com.yf.system.modules.plugin.entity.PluginGroup;
import com.yf.system.modules.plugin.mapper.PluginGroupMapper;
import com.yf.system.modules.plugin.service.PluginGroupService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <p>
* 插件分组业务实现类
* </p>
*
* @author 聪明笨狗
* @since 2022-09-05 10:05
*/
@Service
public class PluginGroupServiceImpl extends ServiceImpl<PluginGroupMapper, PluginGroup> implements PluginGroupService {

    @Override
    public IPage<PluginGroupDTO> paging(PagingReqDTO<PluginGroupDTO> reqDTO) {

        //查询条件
        QueryWrapper<PluginGroup> wrapper = new QueryWrapper<>();

        // 请求参数
        PluginGroupDTO params = reqDTO.getParams();

        //获得数据
        IPage<PluginGroup> page = this.page(reqDTO.toPage(), wrapper);
        //转换结果
        IPage<PluginGroupDTO> pageData = JsonHelper.parseObject(page, new TypeReference<Page<PluginGroupDTO>>(){});
        return pageData;
    }


    @Override
    public void save(PluginGroupDTO reqDTO){
        //复制参数
        PluginGroup entity = new PluginGroup();
        BeanMapper.copy(reqDTO, entity);
        this.saveOrUpdate(entity);
    }

    @Override
    public void delete(List<String> ids){
        //批量删除
        this.removeByIds(ids);
    }

    @Override
    public PluginGroupDTO detail(String id){
        PluginGroup entity = this.getById(id);
        PluginGroupDTO dto = new PluginGroupDTO();
        BeanMapper.copy(entity, dto);
        return dto;
    }

    @Override
    public List<PluginGroupDTO> list(PluginGroupDTO reqDTO){

        //分页查询并转换
        QueryWrapper<PluginGroup> wrapper = new QueryWrapper<>();

        //转换并返回
        List<PluginGroup> list = this.list(wrapper);

        //转换数据
        List<PluginGroupDTO> dtoList = BeanMapper.mapList(list, PluginGroupDTO.class);

        return dtoList;
    }
}
