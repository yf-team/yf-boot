package com.yf.system.modules.user.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
* <p>
* 用户修改请求类
* </p>
*
* @author 聪明笨狗
* @since 2020-04-13 16:57
*/
@Data
@ApiModel(value="用户修改请求类", description="用户修改请求类")
public class SysUserUpdateReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "头像", required=true)
    private String avatar;

    @ApiModelProperty(value = "真实姓名", required=true)
    private String realName;

    @ApiModelProperty(value = "密码", required=true)
    private String password;

    @ApiModelProperty(value = "身份证号码")
    private String idCard;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String email;
}
