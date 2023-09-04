package com.yf.system.modules.role.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
* <p>
* 角色菜单授权实体类
* </p>
*
* @author 聪明笨狗
* @since 2021-03-02 15:44
*/
@Data
@TableName("el_sys_role_menu")
public class SysRoleMenu extends Model<SysRoleMenu> {

    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
    * 角色ID
    */
    @TableField("role_id")
    private String roleId;

    /**
    * 菜单ID
    */
    @TableField("menu_id")
    private String menuId;

}
