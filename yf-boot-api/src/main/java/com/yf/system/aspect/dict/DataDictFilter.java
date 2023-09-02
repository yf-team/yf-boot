package com.yf.system.aspect.dict;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.yf.base.utils.SpringUtils;
import com.yf.system.modules.dict.service.SysDicValueService;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * 数据字典翻译及数据脱敏工作
 *
 * @author van
 */
@Data
@Log4j2
public class DataDictFilter extends JsonSerializer<Object> {

    private static final String DICT_APPEND = "_dictText";

    private String fieldName;
    private String dicCode;
    private String dictTable;
    private String dicText;

    public DataDictFilter(String fieldName, String dicCode, String dictTable, String dicText){
        this.fieldName = fieldName;
        this.dicCode = dicCode;
        this.dictTable = dictTable;
        this.dicText = dicText;
    }

    /**
     * 获取字典业务类
     */
    private SysDicValueService sysDicValueService = SpringUtils.getBean("sysDicValueServiceImpl", SysDicValueService.class);


    @Override
    public void serialize(Object o, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {

        // 获取字段值
        String value = String.valueOf(o);

        // 原字段值不变
        gen.writeObject(o);

        if(StringUtils.isNotBlank(value)){
            // 原字段加上后缀
            String newField = fieldName + DICT_APPEND;
            String trans = this.translate(value);
            // 写入新的字段
            if(StringUtils.isNotBlank(trans)) {
                gen.writeObjectField(newField, trans);
            }
        }
    }


    /**
     * 进行数据字典的翻译
     * @return 翻译后的值
     */
    private String translate(String fieldValue) {

        // 无值不翻译
        if (StringUtils.isEmpty(fieldValue)) {
            return "";
        }

        try {
            // 翻译值
            String dictText;

            if (StringUtils.isEmpty(dictTable)) {
                dictText = sysDicValueService.findDictText(dicCode, fieldValue);
            } else {
                dictText = sysDicValueService.findTableText(dictTable, dicText, dicCode, fieldValue);
            }
            if (!StringUtils.isEmpty(dictText)) {
                return dictText;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
