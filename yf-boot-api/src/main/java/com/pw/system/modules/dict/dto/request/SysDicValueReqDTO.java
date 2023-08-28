package com.pw.system.modules.dict.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
* <p>
* 分类字典值数据传输类
* </p>
*
* @author 聪明笨狗
* @since 2020-12-01 14:00
*/
@Data
@ApiModel(value="数据字典请求类", description="数据字典请求类")
public class SysDicValueReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "归属字典")
    private String dicCode;

    @ApiModelProperty(value = "排除值")
    private List<String> excludes;

    
}
