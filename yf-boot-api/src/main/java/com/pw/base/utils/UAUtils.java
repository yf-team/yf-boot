package com.pw.base.utils;

/**
 * UA工具类判断客户端设备类型
 *
 * @author van
 */
public class UAUtils {

    private final static String[] agent = {"Android", "iPhone", "iPod", "iPad", "Windows Phone", "MQQBrowser"};

    /**
     * 是否手机端
     *
     * @param ua
     * @return
     */
    public static boolean isMobile(String ua) {
        // 桌面系统
        if (ua.contains("Windows NT") || ua.contains("Macintosh")) {
            return false;
        }

        for (String item : agent) {
            if (ua.contains(item)) {
                return true;
            }
        }

        // 默认PC
        return false;
    }


}
