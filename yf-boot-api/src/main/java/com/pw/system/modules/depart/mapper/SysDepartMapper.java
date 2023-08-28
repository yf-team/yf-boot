package com.pw.system.modules.depart.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pw.system.modules.depart.dto.request.DepartQueryReqDTO;
import com.pw.system.modules.depart.dto.response.SysDepartTreeDTO;
import com.pw.system.modules.depart.entity.SysDepart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
* 部门信息Mapper
* </p>
*
* @author 聪明笨狗
* @since 2020-09-02 17:25
*/
public interface SysDepartMapper extends BaseMapper<SysDepart> {

    /**
     * 部门树结构
     * @param query
     * @return
     */
    List<SysDepartTreeDTO> tree(@Param("query") DepartQueryReqDTO query);
}
