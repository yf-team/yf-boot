package com.yf.system.modules.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import java.util.Date;

/**
* <p>
* 登录绑定实体类
* </p>
*
* @author 聪明笨狗
* @since 2021-08-02 14:49
*/
@Data
@TableName("el_sys_user_bind")
public class SysUserBind extends Model<SysUserBind> {

    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
    * 用户ID
    */
    @TableField("user_id")
    private String userId;

    /**
    * 登录类型
    */
    @TableField("login_type")
    private String loginType;

    /**
    * 三方ID
    */
    @TableField("open_id")
    private String openId;

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

}
