package com.pw.ability.captcha.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 图形验证码校验请求类
 * @author bool
 * @date 2020-02-21 10:18
 */
@Data
@ApiModel(value="图形验证码校验请求类", description="图形验证码校验请求类")
public class CheckCaptchaReqDTO implements Serializable {

    /**
     * 验证码键
     */
    @ApiModelProperty(value = "前端产生的验证码键")
    private String captchaKey;

    /**
     * 用户输入的验证码
     */
    @ApiModelProperty(value = "用户输入的验证码")
    private String captchaValue;
}
