package com.yf.system.modules.user.utils;

import com.yf.base.utils.Md5Util;


/**
 * MD5签名验证工具
 * @author bool
 */
public class SignUtils {

    /**
     * 约定的加密密钥
     */
    public static final String SECRET = "JoQEJeD7grF8JdyVaOCvPDNL8arsyvfL";

    /**
     * 校验token是否正确
     * @param userName
     * @param timestamp
     * @param sign
     * @return
     */
    public static boolean checkToken(String userName, Long timestamp, String sign){

        // 拼字符
        String str = String.format("%s-%s-%s", userName, timestamp, SECRET);

        // 进行第二次MD5
        String md5 = Md5Util.md5(str);

        return md5.equals(sign);
    }

    /**
     * 生成Token
     * @param userName
     * @param timestamp
     * @return
     */
    public static String generateToken(String userName, Long timestamp){

        // 拼接字符串
        String str = String.format("%s-%s-%s", userName, timestamp, SECRET);

        // 直接MD5
        String sign = Md5Util.md5(str);

        return sign;
    }


    /**
     * 构建跳转URL
     * @param host
     * @param userName
     * @param realName
     * @param role
     * @return
     */
    public static String generateUrl(String host, String userName, String realName, String role, String departs){

        // 获得当前timestamp
        Long timestamp = System.currentTimeMillis() / 1000;
        String token = generateToken(userName, timestamp);
        // 构建URL
        String url = String.format("%s/api/sys/user/sync-login?userName=%s&realName=%s&timestamp=%s&departs=%s&role=%s&sign=%s",
                host,
                userName,
                realName,
                timestamp,
                departs,
                role,
                token);

        return url;
    }

    public static void main(String [] args) {
        // 构建跳转地址
        String url = generateUrl("http://120.211.98.242:18088", "zhangsan202204143", "可能乱码04143", "student", "北京云帆,技术部");
        System.out.println("跳转地址："+url);
    }
}
