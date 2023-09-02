package com.yf.config.jackson;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.yf.base.api.annon.Dict;
import com.yf.system.aspect.dict.DataDictFilter;

import java.util.List;

/**
 * JSON序列化处理器
 * @author van
 */
public class JacksonSerializerModifier extends BeanSerializerModifier {

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
        for (BeanPropertyWriter beanProperty : beanProperties) {

            // 数据字典翻译
            Dict dict = beanProperty.getAnnotation(Dict.class);
            if (dict != null){
                DataDictFilter dictFieldSerializer = new DataDictFilter(beanProperty.getName(), dict.dicCode(), dict.dictTable(), dict.dicText());
                beanProperty.assignSerializer(dictFieldSerializer);
            }
        }


        return beanProperties;
    }
}
