package com.yf.base.api.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 主键通用响应类，用于添加后返回内容
 * </p>
 *
 * @author 聪明笨狗
 * @since 2019-04-20 12:15
 */
@Data
@ApiModel(value="主键通用响应类", description="主键通用响应类")
@AllArgsConstructor
@NoArgsConstructor
public class BooleanRespDTO extends BaseDTO {

    @ApiModelProperty(value = "主键ID", required=true)
    private Boolean effect;
}
