package com.yf.system.modules.config.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
* <p>
* 通用配置实体类
* </p>
*
* @author 聪明笨狗
* @since 2020-04-17 09:12
*/
@Data
@TableName("el_cfg_base")
public class CfgBase extends Model<CfgBase> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 系统名称
     */
    @TableField("site_name")
    private String siteName;

    /**
     * 登录LOGO
     */
    @TableField("login_logo")
    private String loginLogo;

    /**
     * 登录背景
     */
    @TableField("login_bg")
    private String loginBg;

    /**
     * 后台LOGO
     */
    @TableField("back_logo")
    private String backLogo;

    /**
     * 版权信息
     */
    @TableField("copy_right")
    private String copyRight;

}
