package com.pw.ability.upload.provides.local.service;

import com.pw.ability.upload.provides.local.dto.UploadRespDTO;
import com.pw.ability.upload.provides.local.dto.UploadReqDTO;
import com.pw.ability.upload.provides.local.config.LocalConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 阿里云OSS业务类
 * @author bool 
 * @date 2019-07-12 16:45
 */
public interface LocalUpService {

    /**
     * 文件上传
     * @param reqDTO
     * @return
     */
    UploadRespDTO upload(UploadReqDTO reqDTO);

    /**
     * 文件上传
     * @param localFile
     * @return
     */
    String upload(String localFile);

    /**
     * 下载文件
     * @param request
     * @param response
     */
    void download(HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 获取配置
     * @return
     */
    LocalConfig getConfig();
}
