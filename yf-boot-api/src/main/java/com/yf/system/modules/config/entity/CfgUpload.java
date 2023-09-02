package com.yf.system.modules.config.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
* <p>
* 上传配置实体类
* </p>
*
* @author 聪明笨狗
* @since 2022-03-22 17:46
*/
@Data
@TableName("el_cfg_upload")
public class CfgUpload extends Model<CfgUpload> {

    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
    * 服务提供商
    */
    private String provider;

    /**
    * 是否启用
    */
    private Boolean enabled;

    /**
    * 配置数据
    */
    private String data;

    /**
    * 备注信息
    */
    private String remark;

}
