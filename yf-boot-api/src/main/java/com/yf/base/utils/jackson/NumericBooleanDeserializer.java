package com.yf.base.utils.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * 接收参数时，将数字参数转换为实体的boolean参数
 * @author van
 */
public class NumericBooleanDeserializer extends JsonDeserializer<Boolean> {

    private static final String TRUE_TEXT = "1";
    @Override
    public Boolean deserialize(JsonParser jp, DeserializationContext ctx) throws IOException, JsonProcessingException {
        return jp.getText().equals(TRUE_TEXT);
    }
}
