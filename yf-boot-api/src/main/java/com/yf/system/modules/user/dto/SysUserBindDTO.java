package com.yf.system.modules.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;

/**
* <p>
* 登录绑定数据传输类
* </p>
*
* @author 聪明笨狗
* @since 2021-08-02 14:49
*/
@Data
@ApiModel(value="登录绑定", description="登录绑定")
public class SysUserBindDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "ID", required=true)
    private String id;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "登录类型")
    private String loginType;

    @ApiModelProperty(value = "三方ID")
    private String openId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}
