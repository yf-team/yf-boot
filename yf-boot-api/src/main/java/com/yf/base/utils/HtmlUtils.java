package com.yf.base.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HTML相关工具类
 * @author bool
 */
public class HtmlUtils {


    /**
     * 正则替换内容
     * @param regx
     * @param input
     * @return
     */
    public static String replaceRegx(String regx, String input) {
        Pattern pattern=Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
        Matcher matcher=pattern.matcher(input);
        return matcher.replaceAll("");
    }


    /**
     * 去除文本中的html标签，并返回对应长度的文字内容
     * @param input
     * @param length
     * @return
     */
    public static String splitAndFilterString(String input, int length) {

        if (input == null || input.trim().equals("")) {
            return "";
        }
        // 去掉所有html元素,
        String str = input.trim();

        // 替换样式脚本及HTML标签
        str = replaceRegx("<script[^>]*?>[\\s\\S]*?<\\/script>", str);
        str = replaceRegx("<style[^>]*?>[\\s\\S]*?<\\/style>", str);
        str = replaceRegx("<[^>]+>", str);


        int len = str.length();
        if (len <= length) {
            return str;
        } else {
            str = str.substring(0, length);
            str += "...";
        }
        return str;
    }
}
