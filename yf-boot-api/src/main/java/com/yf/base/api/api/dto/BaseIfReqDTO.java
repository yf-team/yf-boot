package com.yf.base.api.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>
 * 是否状态通用请求
 * </p>
 *
 * @author 聪明笨狗
 * @since 2019-04-20 12:15
 */
@Data
@ApiModel(value="是否状态通用请求", description="是否状态通用请求")
@AllArgsConstructor
@NoArgsConstructor
public class BaseIfReqDTO extends BaseDTO {


    @ApiModelProperty(value = "要修改对象的ID列表", required=true)
    private List<String> ids;

    @ApiModelProperty(value = "启用状态，true/false", required=true)
    private Boolean enabled;
}
