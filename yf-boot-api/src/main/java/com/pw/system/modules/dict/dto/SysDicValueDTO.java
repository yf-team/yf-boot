package com.pw.system.modules.dict.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
* <p>
* 分类字典值数据传输类
* </p>
*
* @author 聪明笨狗
* @since 2020-12-01 14:00
*/
@Data
@ApiModel(value="分类字典值", description="分类字典值")
public class SysDicValueDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "ID/字典编码", required=true)
    private String id;

    @ApiModelProperty(value = "归属字典")
    private String dicCode;

    @ApiModelProperty(value = "子项编码")
    private String value;

    @ApiModelProperty(value = "分类名称")
    private String title;

    @ApiModelProperty(value = "上级ID")
    private String parentId;

    @ApiModelProperty(value = "描述")
    private String remark;
    
}
