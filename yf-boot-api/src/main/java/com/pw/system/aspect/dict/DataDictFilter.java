package com.pw.system.aspect.dict;

import com.alibaba.fastjson.serializer.ValueFilter;
import com.pw.base.api.annon.Dict;
import com.pw.base.api.utils.SpringUtils;
import com.pw.base.utils.InjectUtils;
import com.pw.system.modules.dict.service.SysDicValueService;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

/**
 * 数据字典翻译及数据脱敏工作
 *
 * @author van
 */
@Data
@Log4j2
public class DataDictFilter implements ValueFilter {

    /**
     * 获取字典业务类
     */
    private SysDicValueService sysDicValueService = SpringUtils.getBean("sysDicValueServiceImpl", SysDicValueService.class);

    @Override
    public Object process(Object object, String name, Object value) {

        // 只过滤String和Integer类型
        if (null == value || (!(value instanceof String) && !(value instanceof Integer))) {
            return value;
        }
        try {
            Field field = InjectUtils.getField(object.getClass(), name);
            if (String.class != field.getType() && Integer.class != field.getType()) {
                return value;
            }

            // 数据字典
            Dict dict = field.getAnnotation(Dict.class);
            if (dict != null) {

                // 查找另外字段
                String newField = field.getName() + "_dictText";
                Field valueField = InjectUtils.getField(object.getClass(), newField);

                // 需要在返回类定义与原字段名称匹配的_dictText
                if (value != null) {
                    // 翻译数据并注入
                    String code = dict.dicCode();
                    String text = dict.dicText();
                    String table = dict.dictTable();
                    String key = String.valueOf(value);
                    String dicText = this.transDic(code, text, table, key);
                    valueField.setAccessible(true);
                    valueField.set(object, dicText);
                }
            }

            return value;

        } catch (Exception e) {
            // log.error("当前数据类型为{},值为{}", object.getClass(), value);
            return value;
        }
    }

    /**
     * 翻译字典
     *
     * @param code
     * @param text
     * @param table
     * @param key
     * @return
     */
    private String transDic(String code, String text, String table, String key) {

        if (StringUtils.isEmpty(key)) {
            return null;
        }

        try {
            // 翻译值
            String dictText;

            if (StringUtils.isEmpty(table)) {
                dictText = sysDicValueService.findDictText(code, key.trim());
            } else {
                dictText = sysDicValueService.findTableText(table, text, code, key.trim());
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