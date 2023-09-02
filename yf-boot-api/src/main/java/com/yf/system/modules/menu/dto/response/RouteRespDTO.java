package com.yf.system.modules.menu.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bool
 */
@Data
@ApiModel(value="路由响应类", description="路由响应类")
public class RouteRespDTO implements Serializable {


    private static final long serialVersionUID = 1L;


    @JsonIgnore
    @ApiModelProperty(value = "ID", required=true)
    private String id;

    @JsonIgnore
    @ApiModelProperty(value = "上级菜单")
    private String parentId;

    @ApiModelProperty(value = "菜单类型")
    private Integer menuType;

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

    @ApiModelProperty(value = "子路由表")
    private List<RouteRespDTO> children;

    @ApiModelProperty(value = "路由属性")
    private Map<String,Object> meta;


    /**
     * 获取属性
     * @return
     */
    public Map<String,Object> getMeta(){
        Map<String,Object> meta = new HashMap<>(16);
        if(!StringUtils.isBlank(this.getMetaTitle())){
            meta.put("title", this.getMetaTitle());
        }

        if(!StringUtils.isBlank(this.getMetaIcon())){
            meta.put("icon", this.getMetaIcon());
        }

//        if(this.getMetaNoCache()!=null){
//            meta.put("noCache", this.getMetaNoCache());
//        }

//        if(!StringUtils.isBlank(this.getMetaActiveMenu())){
//            meta.put("activeMenu", this.getMetaActiveMenu());
//        }

        if((hidden==null || !hidden) && menuType.equals(1)){
            meta.put("alwaysShow", true);
        }
        return meta;
    }


}
