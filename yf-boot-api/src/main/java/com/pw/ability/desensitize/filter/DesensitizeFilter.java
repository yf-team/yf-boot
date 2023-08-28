package com.pw.ability.desensitize.filter;

import com.alibaba.fastjson.serializer.ValueFilter;
import com.pw.base.utils.InjectUtils;
import com.pw.ability.desensitize.annon.Desensitized;
import com.pw.ability.desensitize.enums.DesensitizeType;
import com.pw.ability.desensitize.utils.DesensitizeUtils;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Field;

/**
 * 数据脱敏工作
 *
 * @author van
 */
@Data
@Log4j2
public class DesensitizeFilter implements ValueFilter {


    @Override
    public Object process(Object object, String name, Object value) {

        // 只过滤String类型
        if (null == value || !(value instanceof String)) {
            return value;
        }
        try {
            Field field = InjectUtils.getField(object.getClass(), name);
            if (String.class != field.getType()) {
                return value;
            }

            Desensitized ann = field.getAnnotation(Desensitized.class);
            if (ann == null) {
                return value;
            }

            // 字符类型
            String str = String.valueOf(value);

            // JSON字符串脱敏
            if(DesensitizeType.JSON_STRING.equals(ann.type())){
                return DesensitizeUtils.encryptJson(str, ann.props());
            }else{
                return DesensitizeUtils.encrypt(str);
            }

        } catch (Exception e) {
            // log.error("当前数据类型为{},值为{}", object.getClass(), value);
            return value;
        }
    }
}