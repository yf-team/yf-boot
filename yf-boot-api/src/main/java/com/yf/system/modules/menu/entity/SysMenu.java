package com.yf.system.modules.menu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
* <p>
* 系统菜单实体类
* </p>
*
* @author 聪明笨狗
* @since 2021-03-02 13:09
*/
@Data
@TableName("el_sys_menu")
public class SysMenu extends Model<SysMenu> {

    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 上级菜单
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 1目录,2页面,3功能
     */
    @TableField("menu_type")
    private Integer menuType;

    /**
     * 权限标识
     */
    @TableField("permission_tag")
    private String permissionTag;

    /**
     * 访问路径
     */
    private String path;

    /**
     * 视图或Layout
     */
    private String component;

    /**
     * 跳转地址
     */
    private String redirect;

    /**
     * 名称
     */
    private String name;

    /**
     * 路由标题
     */
    @TableField("meta_title")
    private String metaTitle;

    /**
     * 路由标题
     */
    @TableField("meta_icon")
    private String metaIcon;

    /**
     * 高亮菜单
     */
    @TableField("meta_active_menu")
    private String metaActiveMenu;

    /**
     * 是否缓存
     */
    @TableField("meta_no_cache")
    private Boolean metaNoCache;

    /**
     * 是否隐藏
     */
    private Boolean hidden;

    /**
     * 越小越前
     */
    private Integer sort;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;

    /**
     * 修改人
     */
    @TableField("update_by")
    private String updateBy;
}
