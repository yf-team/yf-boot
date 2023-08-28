package com.pw.system.modules.config.dto.request;

import com.pw.ability.upload.provides.local.config.LocalConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
* <p>
* 直播配置信息
* </p>
*
* @author 聪明笨狗
* @since 2020-04-17 09:12
*/
@Data
@ApiModel(value="上传配置信息", description="上传配置信息")
public class UploadConfigReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "直播方案类型")
    private Integer uploadType;

    @ApiModelProperty(value = "本地配置", required=true)
    private LocalConfig localUpload;




    
}
