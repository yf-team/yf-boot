package com.pw.system.modules.user.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
* <p>
* 管理员登录请求类
* </p>
*
* @author 聪明笨狗
* @since 2020-04-13 16:57
*/
@Data
@ApiModel(value="密码修改请求类", description="密码修改请求类")
public class SysUserPassReqDTO implements Serializable {


    @ApiModelProperty(value = "旧密码", required=true)
    private String oldPassword;

    @ApiModelProperty(value = "新密码", required=true)
    private String password;
    
}
