package com.yf.base.api.api.dto;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 分页查询类
 * @param <T>
 * @author bool
 */
@ApiModel(value="分页参数", description="分页参数")
@Data
public class PagingReqDTO<T> {


    @ApiModelProperty(value = "当前页码", required = true, example = "1")
    private Integer current;

    @ApiModelProperty(value = "每页数量", required = true, example = "10")
    private Integer size;

    @ApiModelProperty(value = "查询参数")
    private T params;

    @ApiModelProperty(value = "排序方式")
    private List<OrderItem> orders;

    @JsonIgnore
    @ApiModelProperty(value = "当前用户的ID")
    private String userId;

    /**
     * 转换成MyBatis的简单分页对象
     * @return
     */
    public Page toPage(){
        Page page = new Page();
        page.setCurrent(this.current);
        page.setSize(this.size);
        page.setOrders(this.safeOrders(this.orders));
        return page;
    }

    /**
     * 校验排序字段，防止ORDER BY注入
     * @param items 排序项
     * @return 校验后的排序项
     */
    private List<OrderItem> safeOrders(List<OrderItem> items){
        if(items == null || items.isEmpty()){
            return items;
        }
        for(OrderItem item : items){
            String column = item.getColumn();
            if(column == null || !column.matches("[A-Za-z0-9_]+")){
                throw new IllegalArgumentException("非法的排序字段");
            }
        }
        return items;
    }


}
