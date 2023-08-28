package com.pw.system.modules.user.dto.request;

import com.pw.system.modules.user.dto.SysUserDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
* <p>
* 管理员登录请求类
* </p>
*
* @author 聪明笨狗
* @since 2020-04-13 16:57
*/
@Data
@ApiModel(value="管理员保存请求类", description="管理员保存请求类")
public class SysUserSaveReqDTO extends SysUserDTO {

    @ApiModelProperty(value = "角色列表", required=true)
    private List<String> roles;
}
