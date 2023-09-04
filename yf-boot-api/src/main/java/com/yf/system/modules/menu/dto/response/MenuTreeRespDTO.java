package com.yf.system.modules.menu.dto.response;

import com.yf.base.api.annon.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
* <p>
* 菜单树结构，用于后台菜单管理
* </p>
*
* @author 聪明笨狗
* @since 2021-03-02 13:09
*/
@Data
@ApiModel(value="菜单树结构响应类", description="菜单树结构响应类")
public class MenuTreeRespDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "ID", required=true)
    private String id;

    @ApiModelProperty(value = "上级菜单")
    private String parentId;

    @Dict(dicCode = "menu_type")
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

    @ApiModelProperty(value = "子菜单列表", required=true)
    private List<MenuTreeRespDTO> children;


}
