package com.yf.base.utils;

/**
 * 根据索引获取ABC
 * @author bool
 */
public class AbcTags {


    /**
     * 获取索引对应的ABC
     */
    public static String[] tags = new String[]{
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X"
            , "Y", "Z"
    };

    /**
     * 获得ABC字符
     * @param index
     * @return
     */
    public static String get(int index){
        if(index > tags.length){
            return "";
        }
        return tags[index];
    }
}
