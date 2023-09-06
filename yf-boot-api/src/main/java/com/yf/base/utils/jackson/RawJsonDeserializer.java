package com.yf.base.utils.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * JSON序列化，从前端接收的对象转换成字符
 * @author van
 */
public class RawJsonDeserializer extends JsonDeserializer<String> {

    private static final String TYPE_STRING = "STRING";

    @Override
    public String deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {

        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode node = mapper.readTree(jp);

        // 如果本身就是String类型，直接返回
        if(TYPE_STRING.equals(node.getNodeType().name())){
            return node.textValue();
        }

        return mapper.writeValueAsString(node);
    }
}
