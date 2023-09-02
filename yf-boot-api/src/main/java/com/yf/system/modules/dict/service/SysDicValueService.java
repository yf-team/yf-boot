package com.yf.system.modules.dict.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yf.system.modules.dict.dto.SysDicValueDTO;
import com.yf.system.modules.dict.dto.ext.DicValueTreeDTO;
import com.yf.system.modules.dict.dto.request.SysDicValueReqDTO;
import com.yf.system.modules.dict.entity.SysDicValue;

import java.util.List;
import java.util.Map;

/**
* <p>
* 分类字典值业务类
* </p>
*
* @author 聪明笨狗
* @since 2020-12-01 14:00
*/
public interface SysDicValueService extends IService<SysDicValue> {

    /**
     * 查找分类树
     * @param reqDTO
     * @return
     */
    List<DicValueTreeDTO> findTree(SysDicValueReqDTO reqDTO);

    /**
     * 保存字典项
     * @param reqDTO
     */
    void save(SysDicValueDTO reqDTO);

    /**
     * 查找一个Map列表，显示--值
     * @param dictCode
     * @return
     */
    Map<String,String> findDictMap(String dictCode);

    /**
     * 根据数据字典删除值列表
     * @param codes
     */
    void removeByDict(List<String> codes);

    /**
     * 查找数据字典名称
     * @param dicCode
     * @param value
     * @return
     */
    String findDictText(String dicCode,
                        String value);


    /**
     * 查找其它表数据
     * @param dicTable
     * @param dicText
     * @param dicCode
     * @param value
     * @return
     */
    String findTableText(String dicTable,
                         String dicText,
                         String dicCode,
                         String value);
}
