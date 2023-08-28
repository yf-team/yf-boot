package com.pw.ability.upload.service.impl;

import com.pw.ability.upload.provides.local.service.LocalUpService;
import com.pw.ability.upload.enums.UploadProvider;
import com.pw.ability.upload.service.UploadService;
import com.pw.system.modules.config.dto.CfgUploadDTO;
import com.pw.system.modules.config.service.CfgUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@Service
public class UploadServiceImpl implements UploadService {


    @Autowired
    private LocalUpService localUpService;

    @Autowired
    private CfgUploadService cfgUploadService;




    @Override
    public String upload(String localFile) {

        // 配置信息
        CfgUploadDTO cfg = cfgUploadService.detail("");

        // 本地上传
        if(UploadProvider.LOCAL.equals(cfg.getProvider())){
            return localUpService.upload(localFile);
        }

//        // OSS上传
//        if(UploadProvider.OSS.equals(cfg.getProvider())) {
//            return ossService.upload(localFile);
//        }
//
//        // 七牛云上传
//        if(UploadProvider.QINIU.equals(cfg.getProvider())) {
//            return qiniuService.upload(localFile);
//        }
        // COS文件上传
        if(UploadProvider.COS.equals(cfg.getProvider())) {

        }


        return null;
    }

    @Override
    public String uploadBase64(String base64, String fileName) {

        //将字符串转换为byte数组
        try {
            String localFile = this.base64ToTemp(base64, fileName);
            return this.upload(localFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 将base64转换成本地缓存文件
     * @param base64
     * @param fileName
     * @return
     */
    private String base64ToTemp(String base64, String fileName) throws IOException {

        //将字符串转换为byte数组
        byte[] bytes = new BASE64Decoder().decodeBuffer(base64);
        //为写入文件提供流通道
        String tempPath = System.getProperty("java.io.tmpdir") + "" + fileName;
        System.out.println("++++++临时地址："+tempPath);
        File temp = new File(tempPath);
        FileOutputStream os = new FileOutputStream(temp);
        os.write(bytes);
        os.close();
        return tempPath;
    }
}
