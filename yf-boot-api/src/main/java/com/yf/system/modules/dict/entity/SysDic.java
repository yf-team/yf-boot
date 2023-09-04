package com.yf.system.modules.dict.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
* <p>
* 分类字典实体类
* </p>
*
* @author 聪明笨狗
* @since 2020-12-01 14:00
*/
@Data
@TableName("el_sys_dic")
public class SysDic extends Model<SysDic> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 字典编码
     */
    private String code;

    /**
     * 1分类字典,2数据字典
     */
    private Integer type;

    /**
     * 字典名称
     */
    private String title;

    /**
     * 字典描述
     */
    private String remark;

}
