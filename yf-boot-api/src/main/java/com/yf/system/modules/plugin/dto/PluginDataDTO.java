package com.yf.system.modules.plugin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
* <p>
* 插件信息数据传输类
* </p>
*
* @author 聪明笨狗
* @since 2022-09-05 10:05
*/
@Data
@ApiModel(value="插件信息", description="插件信息")
public class PluginDataDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "ID", required=true)
    private String id;

    @ApiModelProperty(value = "插件名称", required=true)
    private String title;

    @ApiModelProperty(value = "元数据ID", required=true)
    private String schemaId;

    @ApiModelProperty(value = "分组ID", required=true)
    private String groupId;

    @ApiModelProperty(value = "配置数据")
    private String configData;

    @ApiModelProperty(value = "前端页面", required=true)
    private String component;

    @ApiModelProperty(value = "是否使用", required=true)
    private Boolean inUse;

    @ApiModelProperty(value = "插件状态", required=true)
    private String state;

}
