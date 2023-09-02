package com.yf.base.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 资源工具
 * @author bool
 */
public class ResourceUtil {


    /**
     * 数据流转换btye[]
     * @param input
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024*4];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }


    /**
     * 从资源文件写出流
     * @param response
     * @param path
     * @throws Exception
     */
    public static void write(HttpServletResponse response, String path) throws Exception{

        // 获取文件读成流
        InputStream fis = new ClassPathResource(path).getInputStream();
        byte [] data = toByteArray(fis);

        // 设置响应头
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/octet-stream; charset=utf-8");

        // 输出流文件
        OutputStream os = new BufferedOutputStream(response.getOutputStream());
        os.write(data);
        os.flush();
        os.close();
    }

    /**
     * 获取模板临时目录
     * @param create
     * @return
     */
    public static String tempDir(boolean create){
        String path = System.getProperty("java.io.tmpdir");
        if(create){
            File file = new File(path);
            if(!file.exists()){
                file.mkdirs();
            }
        }
        return path;
    }

    /**
     * 获取文件完整地址
     * @param path
     * @return
     */
    public static String fullPath(String path) throws IOException {

        // 临时目录
        String filePath = tempDir(true) + path;
        File file = new File(filePath);
        if(file.exists()){
            return filePath;
        }

        // 获取文件读成流
        InputStream is = new ClassPathResource(path).getInputStream();
        FileUtils.copyInputStreamToFile(is, file);
        return filePath;
    }

    public static void main(String[] args) throws IOException {

        System.out.println(fullPath("tmpl/learn-cert.docx"));
    }
}
