package com.yf.system.modules.config.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
* <p>
* 功能配置实体类
* </p>
*
* @author 聪明笨狗
* @since 2021-11-06 12:02
*/
@Data
@TableName("el_cfg_switch")
public class CfgSwitch extends Model<CfgSwitch> {

    private static final long serialVersionUID = 1L;

    /**
    * 功能名称
    */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
    * 开关或值
    */
    @TableField("val")
    private String val;

}
