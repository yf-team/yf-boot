package com.pw.base.utils;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 集合工具类
 * @author bool
 */
public class ListUtils {

    /**
     * 对比两个列表的值是否一样，不限顺序
     * @param sampleList
     * @param inputList
     * @return
     */
    public static boolean compareList(List<String> sampleList, List<String> inputList){

        // 为空都返回
        if(CollectionUtils.isEmpty(sampleList)
                || CollectionUtils.isEmpty(inputList)){
            return false;
        }

        // 必须数量一样
        if(sampleList.size()!=inputList.size()){
            return false;
        }

        // 比较列表
        for(String item: sampleList){
            if(!contains(inputList, item)){
                return false;
            }
        }

        return true;
    }

    /**
     * 判断文字是否包含
     * @param sampleList
     * @param str
     * @return
     */
    public static boolean contains(List<String> sampleList, String str){

        // 空不比较
        if(CollectionUtils.isEmpty(sampleList) || StringUtils.isEmpty(str)){
            return false;
        }

        for(String item: sampleList){

            if(str.trim().equalsIgnoreCase(item.trim())){
                return true;
            }
        }
        return false;
    }

    /**
     * 列表转换String
     * @param list
     * @return
     */
    public static String listToString(List<String> list){

        if(CollectionUtils.isEmpty(list)){
            return "";
        }

        StringBuffer sb = null;
        for(String str: list){
            if(sb == null){
                sb = new StringBuffer();
            }else{
                sb.append(",");
            }

            sb.append(str);
        }

        return sb.toString();
    }


}
