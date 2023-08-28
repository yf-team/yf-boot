package com.pw.base.api.api.utils;

import com.alibaba.fastjson.JSON;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * HTTP消息工具类，用于在接口错误时，回写一些数据
 * @author bool 
 * @date 2018/11/22 10:58
 */
public class MsgUtils {

    /**
     * 写错误消息
     * @param response
     * @param code
     * @param msg
     */
    public static void writeMessage(ServletResponse response, int code, String msg){
        Map<String,Object> map = new HashMap<>(16);
        map.put("msg", msg);
        map.put("code", code);

        String res = JSON.toJSONString(map);
        PrintWriter writer;

        try {
            writer = response.getWriter();
            writer.write(res);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
