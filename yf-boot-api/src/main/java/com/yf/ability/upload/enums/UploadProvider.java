package com.yf.ability.upload.enums;

/**
 * 上传服务商枚举
 * @author bool
 */
public interface UploadProvider {

    /**
     * 本地上传
     */
    String LOCAL = "local";

    /**
     * 阿里云
     */
    String OSS = "oss";

    /**
     * 腾讯云
     */
    String COS = "cos";

    /**
     * 七牛云
     */
    String QINIU = "qiniu";
}
