package com.pw.base.api.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * token通用请求类
 * </p>
 *
 * @author 聪明笨狗
 * @since 2019-04-20 12:15
 */
@Data
@ApiModel(value="token通用请求类", description="token通用请求类")
public class BaseTokenReqDTO extends BaseDTO {


    @ApiModelProperty(value = "令牌", required=true)
    private String token;

}
