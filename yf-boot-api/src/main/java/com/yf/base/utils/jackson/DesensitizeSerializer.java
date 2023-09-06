package com.yf.base.utils.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置json脱敏相关
 * @author van
 */
@Log4j2
public class DesensitizeSerializer extends JsonSerializer<String> {


    /**
     * 处理需要脱敏的字段
     */
    private final List<String> keys = Arrays.asList("accessKeyId", "accessKeySecret", "appKey", "secretKey", "secretId", "pushKey", "pullKey", "accessKey");

    @Override
    public void serialize(String json, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        Map<String,Object> map = new HashMap<>(16);

        if(StringUtils.isBlank(json)){
            jsonGenerator.writeObject(map);
            return;
        }

        // 进行数据转换
        try {
            map = JsonHelper.parseObject(json, new TypeReference<Map<String, Object>>() {});
        }catch (Exception e){
            jsonGenerator.writeObject(map);
            log.error(e);
            return;
        }

        if(!map.isEmpty()){
            for(String key: map.keySet()){
                Object val = map.get(key);
                Object enc = this.encrypt(key, val);
                map.put(key, enc);
            }
        }

        //自定义处理方式
        jsonGenerator.writeObject(map);
    }


    /**
     * 对字符串进行脱敏，在字符串中间用*号代替，字符串中间有一般的长度会被替代
     * 收尾留下一半，值得注意的是，如果长度小于4，则全部显示为字符串***
     * @param val
     * @return
     */
    public Object encrypt(String key, Object val) {

        // 只过滤String类型
        if(!(val instanceof String)){
            return val;
        }

        String text = String.valueOf(val);

        if (StringUtils.isBlank(text)) {
            return "";
        }

        // 不需要脱敏
        if(!keys.contains(key)){
            return text;
        }

        int length = text.length();

        // 4个字符都没有
        if (length < 4) {
            return "****";
        }

        // 进行一半的数据隐藏
        int hide = length / 2;
        int start = hide / 2;
        int end = start + hide;
        StringBuilder sb = new StringBuilder(text.substring(0, start));
        for (int i = 0; i < hide; i++) {
            sb.append("*");
        }
        sb.append(text.substring(end));
        return sb.toString();
    }


    public static void main(String[] args) {

        String json = "\"{\\\"localDir\\\":\\\"/Users/van/work/yf-boot/\\\",\\\"visitUrl\\\":\\\"http://localhost:8080\\\"}\"";

        json = json.replace("\\\"", "\"").replace("\"{", "{").replace("}\"", "}");
       Map<String,Object> map = JsonHelper.parseObject(json, new TypeReference<Map<String, Object>>() {});
       System.out.println(map.toString());
    }
}
