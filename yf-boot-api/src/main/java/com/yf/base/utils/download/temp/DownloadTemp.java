package com.yf.base.utils.download.temp;

import java.util.List;

/**
 * 下载临时文件存储，用于边下载边将已经读取的数据记录下来，保存在文件中，下次读取自动从断点下载。
 * @author bool
 * @date 2018/8/24 09:02
 */
public class DownloadTemp {

    /**
     * 下载源URL
     */
    private String url;
    /**
     * 文件的总长度
     */
    private long fileLength;
    /**
     * 下载的线程列表
     */
    private List<DownloadTempThread> threads;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getFileLength() {
        return fileLength;
    }

    public void setFileLength(long fileLength) {
        this.fileLength = fileLength;
    }

    public List<DownloadTempThread> getThreads() {
        return threads;
    }

    public void setThreads(List<DownloadTempThread> threads) {
        this.threads = threads;
    }
}
