package com.yf.plugins.upload.local.service.impl;

import com.yf.ability.Constant;
import com.yf.ability.upload.service.UploadService;
import com.yf.base.api.exception.ServiceException;
import com.yf.base.utils.jackson.JsonHelper;
import com.yf.plugins.upload.local.config.LocalConfig;
import com.yf.plugins.upload.local.dto.UploadRespDTO;
import com.yf.plugins.upload.local.utils.OssUtils;
import com.yf.system.modules.plugin.service.PluginDataService;
import lombok.extern.log4j.Log4j2;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 本地上传插件
 * @author van
 */
@Log4j2
@Service
public class LocalUpServiceImpl implements UploadService {


    /**
     * 插件唯一标识
     */
    private static final String PLUGIN_CODE = "upload-local";

    @Autowired
    private PluginDataService pluginDataService;


    @Override
    public UploadRespDTO upload(MultipartFile file) {

        // 查找上传配置
        LocalConfig conf = this.getConfig();

        // 上传文件夹
        String fileDir = conf.getLocalDir();

        // 真实物理地址
        String fullPath;

        try {
            // 新文件
            String filePath = OssUtils.processPath(file);
            // 文件保存地址
            fullPath = fileDir + filePath;
            // 创建文件夹
            OssUtils.checkDir(fullPath);
            // 上传文件
            FileCopyUtils.copy(file.getInputStream(), Files.newOutputStream(Paths.get(fullPath)));

            return this.generateResult(conf, filePath);

        } catch (IOException e) {
            log.error(e);
            throw new ServiceException("文件上传失败："+e.getMessage());
        }
    }


    @Override
    public String upload(String localFile) {
        // 查找上传配置
        LocalConfig conf = this.getConfig();

        // 上传文件夹
        String fileDir = conf.getLocalDir();

        // 真实物理地址
        String fullPath;

        try {

            FileInputStream is = new FileInputStream(localFile);

            // 新文件
            String filePath = OssUtils.renameFile(localFile);
            // 文件保存地址
            fullPath = fileDir + filePath;
            // 创建文件夹
            OssUtils.checkDir(fullPath);
            // 上传文件
            FileCopyUtils.copy(is, Files.newOutputStream(Paths.get(fullPath)));

            return conf.getVisitUrl() + Constant.FILE_PREFIX + filePath;

        } catch (IOException e) {
            log.error(e);
            throw new ServiceException("文件上传失败："+e.getMessage());
        }
    }


    @Override
    public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 查找上传配置
        LocalConfig conf = this.getConfig();

        // 获取真实的文件路径
        String filePath = this.getRealPath(conf, request.getRequestURI());
        File file = new File(filePath);

        if(!file.exists()){
            throw new ServiceException("文件不存在！");
        }

        FileInputStream is = null;
        ServletOutputStream os = null;

        try {

            //获取MimeType
            Tika tika = new Tika();
            String mimeType = tika.detect(file);
            response.setContentType(mimeType);
            response.setContentLength((int)file.length());

            is = new FileInputStream(filePath);
            int len = 0;
            byte[] buffer = new byte[1024];
            os = response.getOutputStream();
            while ((len = is.read(buffer)) > 0) {
                os.write(buffer, 0, len);
            }
        } catch (Exception e){
            log.error(e);
        } finally {
            if(is!=null){
                is.close();
            }
            if(os!=null){
                os.close();
            }
        }
    }

    /**
     * 获取配置文件
     * @return
     */
    private LocalConfig getConfig(){
        String str = pluginDataService.findConfig(PLUGIN_CODE);
        return JsonHelper.parseObject(str, LocalConfig.class);
    }


    /**
     * 构造返回
     * @param fileName
     * @return
     */
    private UploadRespDTO generateResult(LocalConfig conf, String fileName) {

        //获取加速域名
        String domain = conf.getVisitUrl();

        // 返回结果
        return new UploadRespDTO(domain + Constant.FILE_PREFIX + fileName);
    }


    /**
     * 获取真实物理文件地址
     * @param uri
     * @return
     */
    public String getRealPath(LocalConfig conf, String uri){

        String regx = Constant.FILE_PREFIX+"(.*)";

        // 查找全部变量
        Pattern pattern = Pattern.compile(regx);
        Matcher m = pattern.matcher(uri);
        if (m.find()) {
            String str = m.group(1);
            return conf.getLocalDir() + str;
        }

        return null;
    }

}
