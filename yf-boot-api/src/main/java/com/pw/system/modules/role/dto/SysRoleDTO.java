package com.pw.system.modules.role.dto;

import com.pw.base.api.annon.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
* 角色请求类
* </p>
*
* @author 聪明笨狗
* @since 2020-04-13 16:57
*/
@Data
@ApiModel(value="角色", description="角色")
public class SysRoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "角色ID", required=true)
    private String id;

    @ApiModelProperty(value = "角色名称", required=true)
    private String roleName;

    @Dict(dicCode = "data_scope")
    @ApiModelProperty(value = "数据权限", required=true)
    private Integer dataScope;
    private String dataScope_dictText;

    @ApiModelProperty(value = "越大越高", required=true)
    private Integer roleLevel;

    @ApiModelProperty(value = "备注信息")
    private String remark;

    @ApiModelProperty(value = "创建时间", required=true)
    private Date createTime;

    @ApiModelProperty(value = "更新时间", required=true)
    private Date updateTime;

    @ApiModelProperty(value = "创建人", required=true)
    private String createBy;

    @ApiModelProperty(value = "修改人", required=true)
    private String updateBy;

}
