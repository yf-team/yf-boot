package com.yf.base.api.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 支付通用请求类
 * </p>
 *
 * @author 聪明笨狗
 * @since 2019-04-20 12:15
 */
@Data
@ApiModel(value="支付通用请求类", description="支付通用请求类")
public class PayReqDTO extends BaseDTO {

    @ApiModelProperty(value = "下单成功的订单号", required=true)
    private String orderId;
}
