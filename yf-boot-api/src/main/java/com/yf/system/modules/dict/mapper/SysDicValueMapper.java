package com.yf.system.modules.dict.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yf.system.modules.dict.entity.SysDicValue;
import org.apache.ibatis.annotations.Param;

/**
* <p>
* 分类字典值Mapper
* </p>
*
* @author 聪明笨狗
* @since 2020-12-01 14:00
*/
public interface SysDicValueMapper extends BaseMapper<SysDicValue> {

    /**
     * 查找数据字典
     * @param dicCode
     * @param value
     * @return
     */
    String findDictText(@Param("dicCode") String dicCode, @Param("value") String value);

    /**
     * 查找数据字典
     * @param dicTable
     * @param dicText
     * @param dicCode
     * @param value
     * @return
     */
    String findTableText(@Param("dicTable") String dicTable,
                        @Param("dicText") String dicText,
                        @Param("dicCode") String dicCode,
                        @Param("value") String value);
}
