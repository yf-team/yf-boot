package com.pw.system.modules.user.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
* <p>
* 角色菜单授权请求类
* </p>
*
* @author 聪明笨狗
* @since 2020-04-13 16:57
*/
@Data
@ApiModel(value="角色菜单授权请求类", description="角色菜单授权请求类")
public class SysRoleMenuReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID", required=true)
    private String id;

    @ApiModelProperty(value = "菜单ID列表", required=true)
    private List<String> menuIds;
    
}
