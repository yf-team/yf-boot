package com.yf.system.modules.plugin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
* <p>
* 插件信息实体类
* </p>
*
* @author 聪明笨狗
* @since 2022-09-05 10:05
*/
@Data
@TableName("pl_plugin_data")
public class PluginData extends Model<PluginData> {

    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 插件编号
     */
    private String code;

    /**
    * 插件名称
    */
    private String title;

    /**
    * 元数据ID
    */
    @TableField("schema_id")
    private String schemaId;

    /**
    * 分组ID
    */
    @TableField("group_id")
    private String groupId;

    /**
    * 配置数据
    */
    @TableField("config_data")
    private String configData;

    /**
     * 后端服务类
     */
    @TableField("service_clazz")
    private String serviceClazz;

    /**
    * 前端页面
    */
    private String component;

    /**
    * 是否使用
    */
    @TableField("in_use")
    private Boolean inUse;

    /**
    * 插件状态
    */
    private String state;

}
