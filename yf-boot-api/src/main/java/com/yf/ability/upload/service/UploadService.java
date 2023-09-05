package com.yf.ability.upload.service;

import com.yf.plugins.upload.local.dto.UploadRespDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 服务器端上传文件方法
 * @author van
 */
public interface UploadService {

    /**
     * 上传文件
     * @param multipartFile
     * @return
     */
    UploadRespDTO upload(MultipartFile multipartFile);

    /**
     * 本地上传
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
}
