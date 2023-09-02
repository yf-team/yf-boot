package com.yf.base.utils.download.temp;

/**
 * 下载线程信息，临时文件的一部分，用于保存各个线程的下载情况。
 * @author bool
 * @date 2018/8/24 09:03
 */
public class DownloadTempThread {

    /**
     * 线程名称
     */
    private String threadName;
    /**
     * 跳过的字节数
     */
    private long skip;
    /**
     * 读取的字节数
     */
    private long pos;
    /**
     * 已经加载的数据，用于保存进度
     */
    private long loaded;

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
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
}
