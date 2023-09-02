package com.yf.system.modules.plugin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
* <p>
* 插件元数据实体类
* </p>
*
* @author 聪明笨狗
* @since 2022-09-05 10:05
*/
@Data
@TableName("pl_plugin_schema")
public class PluginSchema extends Model<PluginSchema> {

    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
    * 元数据
    */
    private String schemaData;

    /**
    * 分组
    */
    @TableField("group_id")
    private String groupId;

}
