package com.yf.base.utils.download.thread;


import com.yf.base.utils.download.temp.DownloadTempThread;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

/**
 * 文件下载线程，用于多线程下载文件。
 * @author bool
 * @date 2018/8/24 09:20
 */
public class DownloadThread extends Thread {

    /**
     * 日志相关的内容
     */
    private Logger logger = LoggerFactory.getLogger("downloader");

    /**
     * 跳过的长度
     */
    private long skip;

    /**
     * 分段的长度
     */
    private long pos;

    /**
     * 已经下载的字节数，用来统计下载进度
     */
    private long loaded;

    /**
     * 下载地址
     */
    private String url;

    /**
     * 保存路径
     */
    private String dist;

    /**
     * 默认构造
     */
    public DownloadThread() {

    }

    /**
     * 构造方法，输入分段信息
     * @param skip
     * @param pos
     */
    public DownloadThread(String url, String dist, long skip, long pos) {
        this.skip = skip;
        this.pos = pos;
        this.url = url;
        this.dist = dist;
    }

    @Override
    public void run() {

        String threadName = Thread.currentThread().getName();
        logger.info("thread {0} start download", threadName);

        RandomAccessFile raf;
        //连接文件、确定文件头的大小
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(this.url);
        //获取指定的文件段
        get.setHeader("RANGE", "bytes="+this.skip+"-"+this.pos);

        try {
            raf = new RandomAccessFile(this.dist, "rw");
            raf.seek(this.skip);
            CloseableHttpResponse response = client.execute(get);

            //获得文件流
            InputStream is = response.getEntity().getContent();

            int len;
            byte[] b = new byte[1024];
            while ((len = is.read(b)) != -1) {
                raf.write(b, 0, len);
                // 定义已经该线程已下载的字节数
                loaded += len;
            }
            is.close();
            raf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将当前的线程装换成可存储的临时对象，用于临时文件保存。
     * @return
     */
    public DownloadTempThread toTemp(){

        //保存线程加载信息
        DownloadTempThread t = new DownloadTempThread();
        t.setLoaded(this.loaded);
        t.setPos(this.pos);
        t.setSkip(this.skip);
        t.setThreadName(this.getName());
        return t;
    }

    /**
     * 从临时文件中初始化线程，用于从临时文件恢复下载
     * @param url
     * @param dist
     * @param t
     */
    public void fromTemp(String url, String dist, DownloadTempThread t){
        this.url = url;
        this.dist = dist;
        this.skip = t.getSkip() + t.getLoaded();
        this.pos = t.getPos();
        this.loaded = t.getLoaded();
        this.setName(t.getThreadName());
    }


    public long getSkip() {
        return skip;
    }

    public void setSkip(long skip) {
        this.skip = skip;
    }

    public long getPos() {
        return pos;
    }

    public void setPos(long pos) {
        this.pos = pos;
    }

    public long getLoaded() {
        return loaded;
    }

    public void setLoaded(long loaded) {
        this.loaded = loaded;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
