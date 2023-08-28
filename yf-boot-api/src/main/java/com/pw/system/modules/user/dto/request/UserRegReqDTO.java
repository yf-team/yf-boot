package com.pw.system.modules.user.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
* <p>
*
* </p>
*
* @author 聪明笨狗
* @since 2020-04-13 16:57
*/
@Data
@ApiModel(value="用户注册请求类", description="用户注册请求类")
public class UserRegReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "帐号", required=true)
    private String userName;

    @ApiModelProperty(value = "密码", required=true)
    private String password;

    @ApiModelProperty(value = "姓名", required=true)
    private String realName;

    @ApiModelProperty(value = "部门", required=true)
    private String deptCode;

    @ApiModelProperty(value = "验证码KEY", required=true)
    private String captchaKey;

    @ApiModelProperty(value = "验证码值", required=true)
    private String captchaValue;


}
