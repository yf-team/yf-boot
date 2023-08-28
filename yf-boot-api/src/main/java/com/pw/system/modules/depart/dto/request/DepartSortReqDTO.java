package com.pw.system.modules.depart.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
* <p>
* 部门排序请求类
* </p>
*
* @author 聪明笨狗
* @since 2020-03-14 10:37
*/
@Data
@ApiModel(value="拖动排序请求类", description="拖动排序请求类")
public class DepartSortReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "源菜单ID")
    private String form;

    @ApiModelProperty(value = "目标菜单ID")
    private String to;

    @ApiModelProperty(value = "目标类型,inner放入,before放在前面,after放到后面")
    private String dropType;
}
