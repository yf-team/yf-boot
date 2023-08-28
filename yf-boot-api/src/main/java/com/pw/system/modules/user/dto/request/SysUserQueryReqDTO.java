package com.pw.system.modules.user.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
* <p>
* 用户搜索请求类
* </p>
*
* @author 聪明笨狗
* @since 2020-04-13 16:57
*/
@Data
@ApiModel(value="用户搜索请求类", description="用户搜索请求类")
public class SysUserQueryReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "角色ID", required=true)
    private List<String> roleIds;

    @ApiModelProperty(value = "用户名", required=true)
    private String userName;

    @ApiModelProperty(value = "机构编码", required=true)
    private String deptCode;

    @ApiModelProperty(value = "排除列表", required=true)
    private List<String> excludes;

    @ApiModelProperty(value = "状态", required=true)
    private Integer state;

    @ApiModelProperty(value = "手机号码", required=true)
    private String mobile;

}
