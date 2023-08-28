package com.pw.base.api.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <p>
 * 按关键字查询请求通用类
 * </p>
 *
 * @author 聪明笨狗
 * @since 2019-04-20 12:15
 */
@Data
@ApiModel(value="按关键字查询请求通用类", description="按关键字查询请求通用类")
@AllArgsConstructor
@NoArgsConstructor
public class BaseQueryReqDTO extends BaseDTO {


    @ApiModelProperty(value = "日期开始", required=true)
    private Date statDateL;

    @ApiModelProperty(value = "日期结束", required=true)
    private Date statDateR;

    @ApiModelProperty(value = "关键字查询", required=true)
    private String q;
}
