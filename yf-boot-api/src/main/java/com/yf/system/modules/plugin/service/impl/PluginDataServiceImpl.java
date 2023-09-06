package com.yf.system.modules.plugin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.yf.base.api.api.dto.PagingReqDTO;
import com.yf.base.api.exception.ServiceException;
import com.yf.base.utils.BeanMapper;
import com.yf.base.utils.jackson.JsonHelper;
import com.yf.system.modules.plugin.dto.PluginDataDTO;
import com.yf.system.modules.plugin.entity.PluginData;
import com.yf.system.modules.plugin.mapper.PluginDataMapper;
import com.yf.system.modules.plugin.service.PluginDataService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <p>
* 插件信息业务实现类
* </p>
*
* @author 聪明笨狗
* @since 2022-09-05 10:05
*/
@Service
public class PluginDataServiceImpl extends ServiceImpl<PluginDataMapper, PluginData> implements PluginDataService {

    @Override
    public IPage<PluginDataDTO> paging(PagingReqDTO<PluginDataDTO> reqDTO) {

        //查询条件
        QueryWrapper<PluginData> wrapper = new QueryWrapper<>();

        // 请求参数
        PluginDataDTO params = reqDTO.getParams();

        //获得数据
        IPage<PluginData> page = this.page(reqDTO.toPage(), wrapper);
        //转换结果
        System.out.println("++++++service转换了。。。");
        IPage<PluginDataDTO> pageData = JsonHelper.parseObject(page, new TypeReference<Page<PluginDataDTO>>(){});
        return pageData;
    }


    @Override
    public void save(PluginDataDTO reqDTO){
        //复制参数
        PluginData entity = new PluginData();
        BeanMapper.copy(reqDTO, entity);
        this.saveOrUpdate(entity);
    }

    @Override
    public void delete(List<String> ids){
        //批量删除
        this.removeByIds(ids);
    }

    @Override
    public PluginDataDTO detail(String id){
        PluginData entity = this.getById(id);
        PluginDataDTO dto = new PluginDataDTO();
        BeanMapper.copy(entity, dto);
        return dto;
    }

    @Override
    public List<PluginDataDTO> list(PluginDataDTO reqDTO){

        //分页查询并转换
        QueryWrapper<PluginData> wrapper = new QueryWrapper<>();

        //转换并返回
        List<PluginData> list = this.list(wrapper);

        //转换数据
        List<PluginDataDTO> dtoList = BeanMapper.mapList(list, PluginDataDTO.class);

        return dtoList;
    }

    @Override
    public String findConfig(String code) {
        //分页查询并转换
        QueryWrapper<PluginData> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(PluginData::getCode, code);

        PluginData data = this.getOne(wrapper, false);
        if(data == null){
            throw new ServiceException("插件配置不存在！");
        }

        return data.getConfigData();
    }

    @Override
    public String findServiceClazz(String groupId) {
        //分页查询并转换
        QueryWrapper<PluginData> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(PluginData::getGroupId, groupId)
                .orderByDesc(PluginData::getInUse);

        PluginData data = this.getOne(wrapper, false);
        if(data == null){
            throw new ServiceException("插件配置不存在！");
        }

        return data.getServiceClazz();
    }
}
