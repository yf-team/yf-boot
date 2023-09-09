package com.yf.system.modules.user.dto.request;

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
@ApiModel(value="管理员登录请求类", description="管理员登录请求类")
public class SysUserLoginReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名", required=true)
    private String userName;

    @ApiModelProperty(value = "密码", required=true)
    private String password;

    @ApiModelProperty(value = "验证码key", required=true)
    private String captchaKey;

    @ApiModelProperty(value = "用户输入的验证码值", required=true)
    private String captchaValue;

}
