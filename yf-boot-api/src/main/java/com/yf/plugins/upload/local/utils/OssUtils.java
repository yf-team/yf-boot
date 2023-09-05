package com.yf.plugins.upload.local.utils;


import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.yf.base.utils.DateUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

/**
 * 文件上传工具
 * @author bool
 * @date 2019-07-30 21:00
 */
public class OssUtils {

    /**
     * 后缀分割符号
     */
    private static final String SUFFIX_SPLIT = ".";

    /**
     * 重命名文件
     * @param fileName
     * @return
     */
    public static String renameFile(String fileName) {

        //没有后缀名不处理
        if (!fileName.contains(SUFFIX_SPLIT)) {
            return fileName;
        }

        //文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));

        //以系统时间命名
        return IdWorker.getIdStr() + suffix;

    }


    /**
     * 处理新的文件路径，为上传文件预设目录，如：2021/01/01/xxx.jpg，要注意的是，前面没有斜杠
     * @param file 文件
     * @return
     */
    public static String processPath(MultipartFile file){

        // 创建OSSClient实例。
        String fileName = file.getOriginalFilename();

        // 需要重命名
        fileName = OssUtils.renameFile(fileName);

        //获得上传的文件夹
        String dir = DateUtils.formatDate(new Date(), "yyyy/MM/dd/");

        return new StringBuffer(dir).append(fileName).toString();

    }

    /**
     * 处理新的文件路径，为上传文件预设目录，如：2021/01/01/xxx.jpg，要注意的是，前面没有斜杠
     * @param fileName 文件
     * @return
     */
    public static String processPath(String fileName){

        // 需要重命名
        fileName = OssUtils.renameFile(fileName);

        //获得上传的文件夹
        String dir = DateUtils.formatDate(new Date(), "yyyy/MM/dd/");

        return new StringBuffer(dir).append(fileName).toString();

    }

    /**
     * 检查文件夹是否存在，不存在则创建
     * @param fileName
     * @return
     */
    public static void checkDir(String fileName){
        int index = fileName.lastIndexOf("/");
        if(index == -1){
            return;
        }

        File file = new File(fileName.substring(0,index));
        if(!file.exists()){
            file.mkdirs();
        }
    }


}
