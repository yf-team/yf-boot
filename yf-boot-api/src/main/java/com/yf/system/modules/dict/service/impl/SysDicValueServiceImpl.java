package com.yf.system.modules.dict.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yf.base.api.exception.ServiceException;
import com.yf.base.utils.BeanMapper;
import com.yf.ability.redis.service.RedisService;
import com.yf.system.modules.dict.dto.SysDicValueDTO;
import com.yf.system.modules.dict.dto.ext.DicValueTreeDTO;
import com.yf.system.modules.dict.dto.request.SysDicValueReqDTO;
import com.yf.system.modules.dict.entity.SysDicValue;
import com.yf.system.modules.dict.mapper.SysDicValueMapper;
import com.yf.system.modules.dict.service.SysDicValueService;
import com.yf.base.utils.CacheKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* <p>
* 分类字典值业务实现类
* </p>
*
* @author 聪明笨狗
* @since 2020-12-01 14:00
*/
@Service
public class SysDicValueServiceImpl extends ServiceImpl<SysDicValueMapper, SysDicValue> implements SysDicValueService {

    /**
     * 0表示顶级分类
     */
    private static final String ROOT_TAG = "0";

    @Autowired
    private RedisService redisService;

    @Override
    public List<DicValueTreeDTO> findTree(SysDicValueReqDTO reqDTO) {


        String dicCode = reqDTO.getDicCode();

        QueryWrapper<SysDicValue> wrapper = new QueryWrapper();
        wrapper.lambda().eq(SysDicValue::getDicCode, dicCode);

        // 排除不查询的
        if(!CollectionUtils.isEmpty(reqDTO.getExcludes())){
            wrapper.lambda().notIn(SysDicValue::getValue, reqDTO.getExcludes());
        }

        //全部列表
        List<SysDicValue> list = this.list(wrapper);
        List<DicValueTreeDTO> dtoList = BeanMapper.mapList(list, DicValueTreeDTO.class);

        //子结构的列表
        Map<String,List<DicValueTreeDTO>> map = new HashMap<>(16);

        for(DicValueTreeDTO item: dtoList){

            //如果存在
            if(map.containsKey(item.getParentId())){
                map.get(item.getParentId()).add(item);
                continue;
            }

            //增加新的结构
            List<DicValueTreeDTO> a = new ArrayList<>();
            a.add(item);
            map.put(item.getParentId(), a);
        }

        //注意，第0级为顶级的
        List<DicValueTreeDTO> topList = map.get(ROOT_TAG);
        if(!CollectionUtils.isEmpty(topList)){
            for(DicValueTreeDTO item: topList){
                this.fillChildren(map, item);
            }
        }

        return topList;
    }


    @CacheEvict(value = CacheKey.DICT, key = "#reqDTO.dicCode + '-' + #reqDTO.value")
    @Override
    public void save(SysDicValueDTO reqDTO) {

        if(!StringUtils.isBlank(reqDTO.getValue())) {

            QueryWrapper<SysDicValue> wrapper = new QueryWrapper<>();
            wrapper.lambda()
                    .eq(SysDicValue::getDicCode, reqDTO.getDicCode())
                    .eq(SysDicValue::getValue, reqDTO.getValue());

            if (!StringUtils.isBlank(reqDTO.getId())) {
                wrapper.lambda().ne(SysDicValue::getId, reqDTO.getId());
            }
            long count = this.count(wrapper);

            if (count > 0) {
                throw new ServiceException("字典值不可以重复！");
            }
        }else{
            // 分类字典ID和值一样
            reqDTO.setId(IdWorker.getIdStr());
            reqDTO.setValue(reqDTO.getId());
        }

        //复制参数
        SysDicValue entity = new SysDicValue();
        BeanMapper.copy(reqDTO, entity);
        this.saveOrUpdate(entity);
    }

    @Override
    public Map<String, String> findDictMap(String dictCode) {
        QueryWrapper<SysDicValue> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysDicValue::getDicCode, dictCode);


        List<SysDicValue> list = this.list(wrapper);

        Map<String, String> map = new HashMap<>(16);

        if(!CollectionUtils.isEmpty(list)){
            for(SysDicValue item: list){
                map.put(item.getTitle(), item.getValue());
            }
        }
        return map;
    }

    @Override
    public void removeByDict(List<String> codes) {
        QueryWrapper<SysDicValue> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(SysDicValue::getDicCode, codes);
        this.remove(wrapper);
    }


    /**
     * 递归去做填充数据
     * @param map
     * @param item
     */
    private void fillChildren(Map<String,List<DicValueTreeDTO>> map, DicValueTreeDTO item){

        //设置子类
        if(map.containsKey(item.getId())){

            List<DicValueTreeDTO> children = map.get(item.getId());
            if(!CollectionUtils.isEmpty(children)){
                for(DicValueTreeDTO sub: children){
                    this.fillChildren(map, sub);
                }
            }
            item.setChildren(children);
        }
    }



    @Override
    @Cacheable(value = CacheKey.DICT, key = "#dicCode + '-' + #value")
    public String findDictText(String dicCode, String value){
        String text = baseMapper.findDictText(dicCode, value);
        return StringUtils.isBlank(text)?"":text;
    }


    @Override
    public String findTableText(String dicTable, String dicText, String dicCode, String value){

        // 手动缓存，提高短时效率
        String key = MessageFormat.format("{0}-{1}-{2}-{3}-{4}", CacheKey.DICT, dicTable, dicText, dicCode, value);

        String result = redisService.getString(key);
        if(result!=null){
            return result;
        }

        // 查询新数据
        String text = baseMapper.findTableText(dicTable, dicText, dicCode, value);
        if(StringUtils.isBlank(text)){
            text = "";
        }

        // 缓存2分钟有效
        redisService.set(key, text, 120L);
        return text;
    }
}
