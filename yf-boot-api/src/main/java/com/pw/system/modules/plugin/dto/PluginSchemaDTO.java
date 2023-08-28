package com.pw.system.modules.plugin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
* <p>
* 插件元数据数据传输类
* </p>
*
* @author 聪明笨狗
* @since 2022-09-05 10:05
*/
@Data
@ApiModel(value="插件元数据", description="插件元数据")
public class PluginSchemaDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    @ApiModelProperty(value = "ID", required=true)
    private String id;
    
    @ApiModelProperty(value = "元数据")
    private String schemaData;
    
    @ApiModelProperty(value = "分组")
    private String groupId;
    
}
