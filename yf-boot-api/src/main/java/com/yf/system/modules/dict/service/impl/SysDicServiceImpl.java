package com.yf.system.modules.dict.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.yf.base.api.api.dto.PagingReqDTO;
import com.yf.base.api.exception.ServiceException;
import com.yf.base.utils.BeanMapper;
import com.yf.base.utils.jackson.JsonHelper;
import com.yf.system.modules.dict.dto.SysDicDTO;
import com.yf.system.modules.dict.entity.SysDic;
import com.yf.system.modules.dict.mapper.SysDicMapper;
import com.yf.system.modules.dict.service.SysDicService;
import com.yf.system.modules.dict.service.SysDicValueService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
* <p>
* 分类字典业务实现类
* </p>
*
* @author 聪明笨狗
* @since 2020-12-01 14:00
*/
@Service
public class SysDicServiceImpl extends ServiceImpl<SysDicMapper, SysDic> implements SysDicService {


    @Autowired
    private SysDicValueService sysDicValueService;

    @Override
    public IPage<SysDicDTO> paging(PagingReqDTO<SysDicDTO> reqDTO) {


        //查询条件
        QueryWrapper<SysDic> wrapper = new QueryWrapper<>();

        // 请求参数
        SysDicDTO params = reqDTO.getParams();

        if(params!=null){

            if(!StringUtils.isBlank(params.getTitle())){

                wrapper.lambda().and(obj -> obj.like(SysDic::getCode, params.getTitle())
                        .or()
                        .like(SysDic::getTitle, params.getTitle()));
            }

            if(params.getType()!=null){
                wrapper.lambda()
                        .eq(SysDic::getType, params.getType());
            }
        }

        // 按更新时间排序
        wrapper.lambda().orderByDesc(SysDic::getId);

        //获得数据
        IPage<SysDic> page = this.page(reqDTO.toPage(), wrapper);
        //转换结果
        IPage<SysDicDTO> pageData = JsonHelper.parseObject(page, new TypeReference<Page<SysDicDTO>>(){});
        return pageData;
     }

    @Override
    public void save(SysDicDTO reqDTO) {

        QueryWrapper<SysDic> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysDic::getCode, reqDTO.getCode());

        if(!StringUtils.isBlank(reqDTO.getId())){
            wrapper.lambda().ne(SysDic::getId, reqDTO.getId());
        }

        long count = this.count(wrapper);

        if(count > 0){
            throw new ServiceException("分类编码不可以重复！");
        }

        //复制参数
        SysDic entity = new SysDic();
        BeanMapper.copy(reqDTO, entity);
        this.saveOrUpdate(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<String> ids) {
        List<String> codes = new ArrayList<>();
        for(String id: ids){
            SysDic dic = this.getById(id);
            if(dic == null){
                continue;
            }
            codes.add(dic.getCode());
        }

        // 移除主表内容
        this.removeByIds(ids);

        // 移除字典值
        sysDicValueService.removeByDict(codes);
    }
}
