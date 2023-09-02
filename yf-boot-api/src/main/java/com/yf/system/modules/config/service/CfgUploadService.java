package com.yf.system.modules.config.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yf.system.modules.config.dto.CfgUploadDTO;
import com.yf.system.modules.config.entity.CfgUpload;

/**
* <p>
* 上传配置业务接口类
* </p>
*
* @author 聪明笨狗
* @since 2022-03-22 17:46
*/
public interface CfgUploadService extends IService<CfgUpload> {

    /**
     * 添加或保存
     * @param reqDTO
     * @return
     */
    void save(CfgUploadDTO reqDTO);

    /**
     * 根据提供商查找详情
     * @param provider
     * @return
     */
    CfgUploadDTO detail(String provider);
}
