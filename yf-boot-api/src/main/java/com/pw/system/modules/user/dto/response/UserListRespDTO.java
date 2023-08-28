package com.pw.system.modules.user.dto.response;

import com.pw.base.api.annon.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author bool
 */
@Data
@ApiModel(value="用户列表响应类", description="用户列表响应类")
public class UserListRespDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID", required=true)
    private String id;

    @ApiModelProperty(value = "用户名", required=true)
    private String userName;

    @ApiModelProperty(value = "头像", required=true)
    private String avatar;

    @ApiModelProperty(value = "真实姓名", required=true)
    private String realName;

    @Dict(dictTable = "sys_depart", dicCode = "dept_code", dicText = "dept_name")
    @ApiModelProperty(value = "部门编码", required=true)
    private String deptCode;
    private String deptCode_dictText;

    @Dict(dicCode = "user_state")
    @ApiModelProperty(value = "状态", required=true)
    private Integer state;
    private String state_dictText;

    @ApiModelProperty(value = "积分", required=true)
    private Integer points;

    @ApiModelProperty(value = "身份证号码")
    private String idCard;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "创建时间", required=true)
    private Date createTime;

    @ApiModelProperty(value = "更新时间", required=true)
    private Date updateTime;

    @ApiModelProperty(value = "角色名称列表")
    private String roleNames;
}
