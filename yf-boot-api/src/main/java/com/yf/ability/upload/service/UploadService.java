package com.yf.ability.upload.service;

/**
 * 服务器端上传文件方法
 * @author van
 */
public interface UploadService {

    /**
     * 上传文件
     * @return
     */
    String upload(String localFile);

    /**
     * 上传Base64码
     * @param base64
     * @param fileName
     * @return
     */
    String uploadBase64(String base64, String fileName);
}
