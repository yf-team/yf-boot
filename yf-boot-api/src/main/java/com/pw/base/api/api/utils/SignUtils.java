package com.pw.base.api.api.utils;

import com.pw.base.api.utils.file.MD5Util;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 签名生成工具，用于产生数据的签名和回签
 *
 * @author dav
 * @date 2018/11/24 11:51
 */
@Log4j2
public class SignUtils {

    /**
     * app+version为签名的key，作为后端的签名使用
     */
    private static final Map<String, String> SECRETS = new HashMap<String, String>() {{
        //通用接口签名
        put("fomille-1000", "o0tUGNebDK1ApPFn9Hn75TRGCy1yugRH");
    }};


    /**
     * 参数签名算法，对获取的参数进行签名校验，以及对返回的参数进行回签校验
     * @param timestamp
     * @return
     */
    public static boolean checkSign(String app, String version, String sign, String timestamp) {

        //获得秘钥
        String secret = SECRETS.get(app + "-" + version);
        //没有秘钥、非法访问
        if (StringUtils.isBlank(secret)) {
            return false;
        }

        StringBuffer sb = new StringBuffer(app)
                .append("&").append(version)
                .append("&").append(secret)
                .append("&").append(timestamp);

        log.info("++++++++++sign str is:"+sb.toString());
        String mySign = MD5Util.MD5(sb.toString());
        log.info("++++++++++true sign is:"+mySign);
        return mySign.equals(sign);
    }

}
