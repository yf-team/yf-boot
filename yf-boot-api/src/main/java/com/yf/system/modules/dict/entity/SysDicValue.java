package com.yf.system.modules.dict.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
* <p>
* 分类字典值实体类
* </p>
*
* @author 聪明笨狗
* @since 2020-12-01 14:00
*/
@Data
@TableName("el_sys_dic_value")
public class SysDicValue extends Model<SysDicValue> {

    private static final long serialVersionUID = 1L;

    /**
     * ID/字典编码
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 归属字典
     */
    @TableField("dic_code")
    private String dicCode;

    /**
     * 子项编码
     */
    @TableField("`value`")
    private String value;

    /**
     * 分类名称
     */
    private String title;

    /**
     * 上级ID
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 描述
     */
    private String remark;

}
