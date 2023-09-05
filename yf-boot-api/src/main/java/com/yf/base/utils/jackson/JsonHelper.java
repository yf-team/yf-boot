package com.yf.base.utils.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.IOException;


/**
 * JSON工具类
 * @author van
 */
public class JsonHelper {

    /**
     * 转换为字符串
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {

        ObjectMapper mapper = getMapper();

        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将字符转换为java对象
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        ObjectMapper mapper = getMapper();
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将对象转换为另外一个对象
     * @param object
     * @param clazz
     * @return
     */
    public static <T> T parseObject(Object object, Class<T> clazz) {
        ObjectMapper mapper = getMapper();
        try {
            return mapper.readValue(toJson(object), clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 复杂对象的转换
     * @param object
     * @param typeReference
     * @return
     * @param <T>
     */
    public static <T> T parseObject(Object object, TypeReference<T> typeReference) {
        ObjectMapper mapper = getMapper();
        try {
            return mapper.readValue(toJson(object), typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 复杂对象的转换
     * @param json
     * @param typeReference
     * @return
     * @param <T>
     */
    public static <T> T parseObject(String json, TypeReference<T> typeReference) {
        ObjectMapper mapper = getMapper();
        try {
            return mapper.readValue(json, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取转换配置
     * @return
     */
    public static ObjectMapper getMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true) ;
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        return objectMapper;
    }

    @Data
    public static class TestDD{
        private static final long serialVersionUID = 1L;


        @ApiModelProperty(value = "ID", required=true)
        private String id;

        @ApiModelProperty(value = "存储Bucket")
        private String bucket;

        @ApiModelProperty(value = "秘钥ID")
        private String accessKeyId;

        @ApiModelProperty(value = "秘钥密码")
        private String accessKeySecret;

        @ApiModelProperty(value = "节点")
        private String endpoint;

        @ApiModelProperty(value = "访问路径")
        private String url;

        @ApiModelProperty(value = "智能媒体项目名")
        private String project;

        @ApiModelProperty(value = "MPS管道ID")
        private String pipeline;
    }

    public static void main(String[] args) {

        String oriResourceStr = "{\"accessKeyId\":\"LTAI5tPdS3emyNm8yU464pJU\",\"pipeline\":\"77bbcf2b2b3f4b5f945f57810ba81aec\",\"bucket\":\"yf-exam-prod\",\"accessKeySecret\":\"ITyqpdvSH4ZLP6pB8E7iqTefCA86X4\",\"endpoint\":\"oss-cn-beijing\",\"project\":\"ossdocdefault\",\"url\":\"https://files.yfhl.net/\"}";
        TestDD oriResource = JsonHelper.parseObject(oriResourceStr, TestDD.class);

        System.out.println(oriResource);


    }
}
