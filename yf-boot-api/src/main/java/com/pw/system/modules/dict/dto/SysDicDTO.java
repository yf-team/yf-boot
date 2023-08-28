package com.pw.system.modules.dict.dto;

import com.pw.base.api.annon.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
* <p>
* 分类字典数据传输类
* </p>
*
* @author 聪明笨狗
* @since 2020-12-01 14:00
*/
@Data
@ApiModel(value="分类字典", description="分类字典")
public class SysDicDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "ID", required=true)
    private String id;

    @ApiModelProperty(value = "字典编码")
    private String code;

    @Dict(dicCode = "dic_type")
    @ApiModelProperty(value = "1分类字典,2数据字典")
    private Integer type;
    private String type_dictText;

    @ApiModelProperty(value = "字典名称")
    private String title;

    @ApiModelProperty(value = "字典描述")
    private String remark;
    
}
