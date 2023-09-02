package com.yf.system.modules.menu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
* 系统菜单数据传输类
* </p>
*
* @author 聪明笨狗
* @since 2021-03-02 13:09
*/
@Data
@ApiModel(value="系统菜单", description="系统菜单")
public class SysMenuDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "ID", required=true)
    private String id;

    @ApiModelProperty(value = "上级菜单")
    private String parentId;

    @ApiModelProperty(value = "1菜单2功能", required=true)
    private Integer menuType;

    @ApiModelProperty(value = "权限标识")
    private String permissionTag;

    @ApiModelProperty(value = "访问路径")
    private String path;

    @ApiModelProperty(value = "视图或Layout")
    private String component;

    @ApiModelProperty(value = "跳转地址")
    private String redirect;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "路由标题")
    private String metaTitle;

    @ApiModelProperty(value = "路由标题")
    private String metaIcon;

    @ApiModelProperty(value = "高亮菜单")
    private String metaActiveMenu;

    @ApiModelProperty(value = "是否缓存")
    private Boolean metaNoCache;

    @ApiModelProperty(value = "是否隐藏")
    private Boolean hidden;

    @ApiModelProperty(value = "越小越前")
    private Integer sort;

    @ApiModelProperty(value = "创建时间", required=true)
    private Date createTime;

    @ApiModelProperty(value = "更新时间", required=true)
    private Date updateTime;

    @ApiModelProperty(value = "创建人", required=true)
    private String createBy;

    @ApiModelProperty(value = "修改人", required=true)
    private String updateBy;

    @ApiModelProperty(value = "数据标识", required=true)
    private Integer dataFlag;

}
