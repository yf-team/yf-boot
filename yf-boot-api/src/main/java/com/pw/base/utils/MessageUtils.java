package com.pw.base.utils;

import com.alibaba.fastjson.JSON;
import com.pw.base.api.api.ApiRest;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 消息工具类
 * @author bool
 * @date 2019-07-17 09:32
 */
@Log4j2
@Component
public class MessageUtils {


    /**
     * 通过response写出JSON数据
     * @param response
     */
    public static void writeError(HttpServletResponse response, String msg) {

        try {

            //固定错误
            ApiRest apiRest = new ApiRest();
            apiRest.setMsg(msg);
            apiRest.setCode(1);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write(JSON.toJSONString(apiRest));
            response.getWriter().close();

        }catch (IOException e){

        }
    }


}
