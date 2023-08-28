package com.pw.ability.desensitize.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 字符脱敏处理类
 * @author van
 */
public class DesensitizeUtils {

    /**
     * 对字符串进行脱敏，在字符串中间用*号代替，字符串中间有一般的长度会被替代
     * 收尾留下一半，值得注意的是，如果长度小于4，则全部显示为字符串***
     * @param text
     * @return
     */
    public static String encrypt(String text) {

        if (StringUtils.isBlank(text)) {
            return "";
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
        StringBuffer sb = new StringBuffer(text.substring(0, start));
        for (int i = 0; i < hide; i++) {
            sb.append("*");
        }
        sb.append(text.substring(end));
        return sb.toString();
    }

    /**
     * JSON字符串进行脱敏
     * @param str
     * @return
     */
    public static String encryptJson(String str, String [] props){
        Map<String,Object> map = JSON.parseObject(str, Map.class);
        for(String key: map.keySet()){

            // 只处理指定的参数
            if(!inArray(props, key)){
                continue;
            }

            String value = String.valueOf(map.get(key));
            String en = encrypt(value);
            map.put(key, en);
        }

        return JSON.toJSONString(map);
    }

    /**
     * 判断字符是否在数组里面
     * @param arr
     * @param str
     * @return
     */
    public static boolean inArray(String [] arr, String str){
        for(String pop: arr){
            if(pop.equals(str)){
                return true;
            }
        }
        return false;
    }
}
