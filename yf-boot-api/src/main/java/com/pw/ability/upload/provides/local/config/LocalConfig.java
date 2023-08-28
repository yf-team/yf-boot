package com.pw.ability.upload.provides.local.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
* <p>
* 本地文件上传配置数据传输类
* </p>
*
* @author 聪明笨狗
* @since 2021-02-05 11:16
*/
@Data
@ApiModel(value="本地文件上传配置", description="本地文件上传配置")
public class LocalConfig implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    @ApiModelProperty(value = "ID", required=true)
    private String id;
    
    @ApiModelProperty(value = "本地目录地址")
    private String localDir;
    
    @ApiModelProperty(value = "访问路径")
    private String url;
    
}
