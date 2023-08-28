package com.pw.system.modules.dict.dto.ext;

import com.pw.system.modules.dict.dto.SysDicValueDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
* <p>
* 分类字典值数据传输类
* </p>
*
* @author 聪明笨狗
* @since 2020-12-01 14:00
*/
@Data
@ApiModel(value="分类字典值", description="分类字典值")
public class DicValueTreeDTO extends SysDicValueDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "子类列表")
    private List<DicValueTreeDTO> children;
    
}
