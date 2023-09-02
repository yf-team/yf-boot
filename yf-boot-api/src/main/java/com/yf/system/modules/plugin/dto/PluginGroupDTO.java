package com.yf.system.modules.plugin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
* <p>
* 插件分组数据传输类
* </p>
*
* @author 聪明笨狗
* @since 2022-09-05 10:05
*/
@Data
@ApiModel(value="插件分组", description="插件分组")
public class PluginGroupDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "ID", required=true)
    private String id;

    @ApiModelProperty(value = "分组名称")
    private String title;

    @ApiModelProperty(value = "独立排斥")
    private Boolean single;

}
