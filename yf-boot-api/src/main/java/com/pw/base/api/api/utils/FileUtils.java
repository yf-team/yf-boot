package com.pw.base.api.api.utils;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 文件工具类
 * @author van
 */
public class FileUtils {

    /**
     * http头常量
     */
    public static final String URL_SCHEMA_HTTP = "http://";
    public static final String URL_SCHEMA_HTTPS = "https://";

    /**
     * 获取文件大小，支持本地文件及http文件地址
     * @param file
     * @return
     */
    public static String getFileSize(String file){

        // 网络地址获取文件头大小
        if(file.toLowerCase().startsWith(URL_SCHEMA_HTTP)
                || file.toLowerCase().startsWith(URL_SCHEMA_HTTPS)){
            return getFileSizeFromUrl(file);
        }

        return getFileSizeFromFile(file);
    }

    /**
     * 从http获取文件大小
     * @param httpUrl
     * @return
     */
    public static String getFileSizeFromUrl(String httpUrl){

        URL url = null;
        HttpURLConnection conn = null;
        try {

            url = new URL(httpUrl);
            conn = (HttpURLConnection) url.openConnection();
            //根据响应获取文件大小
            int length =  conn.getContentLength();
            // 获取文件大小
            return formatFileSize(length);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(conn!=null){
                conn.disconnect();
            }
        }
        return "";
    }

    /**
     * 获取本地文件大小
     * @param path
     * @return
     */
    public static String getFileSizeFromFile(String path){

        File file = new File(path);
        if(file!=null && file.isFile()){
           long size = file.length();
           return formatFileSize(size);
        }

        return "";
    }


    /**
     * 格式化文件大小
     * @param data
     * @return
     */
    public static String formatFileSize(long data){

        if (data > 0) {

            double size = data * 1d;

            double kiloByte = size / 1024;
            if (kiloByte < 1 && kiloByte > 0) {
                //不足1K
                return String.format("%.2fByte",size);
            }
            double megaByte = kiloByte / 1024;
            if (megaByte < 1) {
                //不足1M
                return String.format("%.2fK", kiloByte);
            }

            double gigaByte = megaByte / 1024;
            if (gigaByte < 1) {
                //不足1G
                return String.format("%.2fM", megaByte);
            }

            double teraByte = gigaByte / 1024;
            if (teraByte < 1) {
                //不足1T
                return String.format("%.2fG", gigaByte);
            }

            return String.format("%.2fT", teraByte);
        }
        return "0K";
    }

}
