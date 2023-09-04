package com.yf.system.modules.role.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
* <p>
* 角色实体类
* </p>
*
* @author 聪明笨狗
* @since 2020-04-13 16:57
*/
@Data
@TableName("el_sys_role")
public class SysRole extends Model<SysRole> {

    private static final long serialVersionUID = 1L;

    /**
    * 角色ID
    */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;

    /**
     * 数据权限
     */
    @TableField("data_scope")
    private Integer dataScope;

    /**
     * 越大越高
     */
    @TableField("role_level")
    private Integer roleLevel;

    /**
     * 备注信息
     */
    private String remark;

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
