package com.yf.system.modules.config.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yf.base.utils.jackson.DesensitizeSerializer;
import com.yf.base.utils.jackson.RawJsonDeserializer;
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

    @JsonSerialize(using = DesensitizeSerializer.class)
    @JsonDeserialize(using = RawJsonDeserializer.class)
    @ApiModelProperty(value = "配置数据")
    private String data;

    @ApiModelProperty(value = "备注信息")
    private String remark;

}
