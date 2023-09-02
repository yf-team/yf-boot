package com.yf.base.utils.download;

import com.yf.base.utils.download.temp.DownloadTemp;
import com.yf.base.utils.download.temp.DownloadTempThread;
import com.yf.base.utils.download.thread.DownloadThread;
import com.yf.base.utils.file.MD5Util;
import com.yf.base.utils.file.TextFileUtils;
import com.yf.base.utils.jackson.JsonHelper;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * JAVA多线程断点下载工具，支持多线程下载和断点续传
 *
 * @author bool
 * @date 2018/8/23 08:49
 */
public class Downloader {

    /**
     * 正确响应
     */
    private static final int RESPONSE_OK = 200;

    /**
     * 临时文件的后缀名
     */
    private static final String TEMP_FILE_SUFFIX = ".jd";

    /**
     * 同时下载的线程数
     */
    private int threadCount = 5;

    /**
     * 日志相关的内容
     */
    private Logger logger = LoggerFactory.getLogger("downloader");

    /**
     * 总大小
     */
    private long fileLength;

    /**
     * 已下载的大小
     */
    private long downloaded;

    /**
     * 下载文件的URL
     */
    private String url;

    /**
     * 保存文件的路径
     */
    private String dist;

    /**
     * 下载线程
     */
    private DownloadThread[] threads = new DownloadThread[threadCount];

    /**
     * 定时器，用于保存下载进度
     */
    private ScheduledExecutorService executorService;


    /**
     * 构造下载器，传入下载地址和文件保存路径
     *
     * @param url
     * @param dist
     */
    public Downloader(String url, String dist) {
        this.url = url;
        this.dist = dist;
    }

    /**
     * 下载文件入口，下载成功返回true
     *
     * @return
     * @throws Exception
     */
    public void download() throws Exception {

        //开启断点下载
        if (checkNeedContinue()) {
            this.continueDownload();
        } else {
            //开启全新的下载
            this.newDownload();
        }

        //运行定时任务，保存下载进度
        executorService = new ScheduledThreadPoolExecutor(1, new BasicThreadFactory.Builder().namingPattern("scheduled-pool-%d").daemon(true).build());
        executorService.scheduleAtFixedRate(() -> saveProcess(), 0, 800, TimeUnit.MILLISECONDS);
    }

    /**
     * 开启全新的下载
     *
     * @return
     * @throws Exception
     */
    private void newDownload() throws Exception {
        //连接文件、确定文件头的大小
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse response = client.execute(get);

        int statusCode = response.getStatusLine().getStatusCode();
        //如果URL存在重定向，则获取重定向后的URL拿来下载
        if (statusCode != RESPONSE_OK) {
            throw new Exception("URL响应状态不正确：" + statusCode);
        }

        //获取文件的长度
        fileLength = response.getEntity().getContentLength();

        //每个线程下载的数据
        long pie = fileLength / threadCount;
        long left = fileLength % threadCount;

        for (int i = 0; i < threadCount; i++) {

            long start = i * pie;
            long end = (i + 1) * pie;
            //最后的线程连剩下的也一起下了
            if ((i + 1) == threadCount) {
                end += left;
            }
            //第二段开始索引+1
            if (i > 0) {
                start += 1;
            }

            logger.info("start download form bytes {0} to {1}", start, end);
            //加入线程池下载
            String threadName = MD5Util.MD5(url) + "-" + i;
            DownloadThread thread = new DownloadThread(url, dist, start, end);
            thread.setName(threadName);
            threads[i] = thread;
            threads[i].start();
        }

    }

    /**
     * 检查全部线程是否还是活动的，如果有一个没完成，则说明下载还在继续
     *
     * @return
     */
    public boolean isDownloading() {
        for (DownloadThread thread : threads) {
            if (thread.isAlive()) {
                return true;
            }
        }
        return false;
    }


    /**
     * 检测是否需要断点下载
     * @return
     */
    public boolean checkNeedContinue() {
        //检查是否需要断点下载
        File tempFile = new File(dist + TEMP_FILE_SUFFIX);

        //开启断点下载
        return tempFile.exists();
    }

    /**
     * 断点继续下载，主要根据描述文件是否存在来做区分
     */
    private void continueDownload() {

        //开启断点下载
        String json = TextFileUtils.readText(dist + TEMP_FILE_SUFFIX);
        DownloadTemp info = JsonHelper.parseObject(json, DownloadTemp.class);
        this.fileLength = info.getFileLength();

        //如果信息读取不正确或者没有要下载的线程，直接返回
        if (info == null || info.getThreads()!=null || info.getThreads().size()==0) {
            return;
        }

        //根据文件的存储重新设置下载线程
        this.threadCount = info.getThreads().size();
        this.threads = new DownloadThread[this.threadCount];

        int i = 0;
        for (DownloadTempThread t : info.getThreads()) {
            DownloadThread thread = new DownloadThread();
            thread.fromTemp(this.url, this.dist, t);
            threads[i] = thread;
            threads[i].start();
            i++;
        }

    }


    /**
     * 保存下载进度
     */
    public void saveProcess() {

        List<DownloadTempThread> records = new ArrayList<>();
        long downloaded = 0;

        for (DownloadThread thread : this.threads) {
            //保存线程加载信息
            records.add(thread.toTemp());
            //累计进度
            downloaded += thread.getLoaded();
        }

        DownloadTemp info = new DownloadTemp();
        info.setFileLength(fileLength);
        info.setUrl(url);
        info.setThreads(records);

        //写入临时文件以备断点下载
        TextFileUtils.write(dist + TEMP_FILE_SUFFIX, JsonHelper.toJson(info));

        //已下载的数量
        this.downloaded = downloaded;
    }


    /**
     * 获取文件的大小
     * @return
     */
    public long getFileLength() {
        return fileLength;
    }

    /**
     * 获取已下载的大小
     * @return
     */
    public long getDownloaded() {
        return downloaded;
    }


    /**
     * 测试方法，用于测试下载
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {


        //下载参数
//        String url = "https://cdn-file1.sitebuilding.cn/resource.zip";
//        String dist = "/Users/bool/Desktop/resource.zip";

        String url = "https://cdn-file1.sitebuilding.cn/itranslate.dmg";
        String dist = "/Users/bool/Desktop/itranslate.dmg";


        //开启下载
        Downloader downloader = new Downloader(url, dist);
        downloader.download();

        //如果线程还是活动的，则计算下载进度
        while (downloader.isDownloading()) {
            //等待获取下载进度
            Thread.sleep(800);
            //计算下载进度
            float percent = downloader.getDownloaded() * 100f / downloader.getFileLength();
            System.out.println("+++++下载进度为:" + percent + "%");
        }

        //可以做一些文件的完整性校验
        String md5 = MD5Util.getFileMD5(new File(dist));
        System.out.println("+++++下载件MD5:" + md5);

        //比较源文件的MD5
        String example = "/Users/bool/Downloads/itranslate.dmg";
        String exMD5 = MD5Util.getFileMD5(new File(example));
        System.out.println("+++++参考件MD5:" + exMD5);

    }
}
