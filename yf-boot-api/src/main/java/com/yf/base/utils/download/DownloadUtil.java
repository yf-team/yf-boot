package com.yf.base.utils.download;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 下载工具类，用于将网络文件本地化，简单的下载，没有断点下载和重试机制；
 * @author bool
 * @date 2018/7/30 09:16
 */
public class DownloadUtil {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger("downloader");

    /**
     * 将Nginx配置文件本地化
     * @param url
     * @param dir
     * @return
     * @throws Exception
     */
    public static String download(String url, String dir, String fileName) throws Exception {
        // 创建HttpClient
        CloseableHttpClient client = HttpClients.createDefault();

        File dirFile = new File(dir);
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }

        //获得文件名
        if(StringUtils.isEmpty(fileName)){
            fileName = extractUrlFileName(url);
        }
        try {
            HttpGet httpGet = new HttpGet(url);
            logger.info("executing request: " + httpGet.getURI());
            HttpResponse response = client.execute(httpGet);
            File targetFile = new File(dir, fileName);
            if(targetFile.exists()){
                targetFile.delete();
            }
            FileOutputStream fos = new FileOutputStream(targetFile);
            // 得到网络资源的字节数组,并写入文件
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream is = entity.getContent();
                try {
                    byte b[] = new byte[1024];
                    int j;
                    while ((j = is.read(b)) != -1) {
                        fos.write(b, 0, j);
                    }
                    fos.flush();
                    fos.close();
                } catch (Exception ex) {
                    throw ex;
                } finally {
                    is.close();
                }
                if (targetFile.exists()) {
                    return targetFile.getCanonicalPath();
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            client.close();
        }
        return null;
    }


    /**
     * 从URL中提取文件名称
     * @param url
     * @return
     */
    private static String extractUrlFileName(String url) {
        int index = url.lastIndexOf("/");
        if (index != -1) {
            return url.substring(index+1);
        }
        return null;
    }

}
