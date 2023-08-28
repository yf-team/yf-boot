package com.pw.system.modules.config.dto;

import com.pw.ability.desensitize.annon.Desensitized;
import com.pw.ability.desensitize.enums.DesensitizeType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
* <p>
* 上传配置数据传输类
* </p>
*
* @author 聪明笨狗
* @since 2022-03-22 17:46
*/
@Data
@ApiModel(value="上传配置", description="上传配置")
public class CfgUploadDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    @ApiModelProperty(value = "ID", required=true)
    private String id;
    
    @ApiModelProperty(value = "服务提供商")
    private String provider;
    
    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;

    @Desensitized(type = DesensitizeType.JSON_STRING, props = {"accessKeyId", "accessKeySecret", "appKey", "secretKey", "secretId", "pipeline"})
    @ApiModelProperty(value = "配置数据")
    private String data;
    
    @ApiModelProperty(value = "备注信息")
    private String remark;
    
}
