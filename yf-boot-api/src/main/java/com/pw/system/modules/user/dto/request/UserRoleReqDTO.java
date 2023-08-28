package com.pw.system.modules.user.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author bool
 */
@Data
@ApiModel(value="用户角色批量操作请求类", description="用户角色批量操作请求类")
public class UserRoleReqDTO implements Serializable {

    @ApiModelProperty(value = "用户列表", required=true)
    private List<String> userIds;

    @ApiModelProperty(value = "角色列表", required=true)
    private List<String> roleIds;

    @ApiModelProperty(value = "操作1增加，0移除", required=true)
    private Integer flag;
}
