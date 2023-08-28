package com.pw.base.api.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 通用批量操作请求类
 * </p>
 *
 * @author 聪明笨狗
 * @since 2019-04-20 12:15
 */
@Data
@ApiModel(value="通用批量操作请求类", description="通用批量操作请求类")
public class BaseBatchReqDTO extends BaseDTO {

    @ApiModelProperty(value = "要修改的对象列表", required=true)
    private List<String> ids;

    @ApiModelProperty(value = "修改成为的值", required=true)
    private String toId;
}
