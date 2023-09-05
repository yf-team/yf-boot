package com.yf.plugins.upload.local.controller;


import com.yf.ability.Constant;
import com.yf.ability.upload.factory.UploadFactory;
import com.yf.base.api.api.ApiRest;
import com.yf.base.api.api.controller.BaseController;
import com.yf.plugins.upload.local.dto.UploadReqDTO;
import com.yf.plugins.upload.local.dto.UploadRespDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 本地文件上传下载请求类
 * @author bool
 */
@Log4j2
@Api(tags = {"文件上传"})
@RestController
public class UploadController extends BaseController {

    @Autowired
    private UploadFactory uploadFactory;

    /**
     * 文件上传
     * @param reqDTO
     * @return
     */
    @PostMapping("/api/common/file/upload")
    @ApiOperation(value = "文件上传", notes = "此接口较为特殊，参数都通过表单方式提交，而非JSON")
    public ApiRest<UploadRespDTO> upload(@ModelAttribute UploadReqDTO reqDTO) {
        // 上传并返回URL
        UploadRespDTO respDTO = uploadFactory.getService().upload(reqDTO.getFile());
        return super.success(respDTO);
    }

    /**
     * 独立文件下载
     * @param request
     * @param response
     */
    @GetMapping(Constant.FILE_PREFIX+"**")
    @ApiOperation(value = "文件下载", notes = "文件下载")
    public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        uploadFactory.getService().download(request, response);
    }
}
