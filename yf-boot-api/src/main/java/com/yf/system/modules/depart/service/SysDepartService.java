package com.yf.system.modules.depart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yf.system.modules.depart.dto.SysDepartDTO;
import com.yf.system.modules.depart.dto.request.DepartSortReqDTO;
import com.yf.system.modules.depart.dto.response.SysDepartTreeDTO;
import com.yf.system.modules.depart.entity.SysDepart;

import java.util.List;

/**
* <p>
* 部门信息业务类
* </p>
*
* @author 聪明笨狗
* @since 2020-09-02 17:25
*/
public interface SysDepartService extends IService<SysDepart> {

    /**
     * 保存
     * @param reqDTO
     */
    void save(SysDepartDTO reqDTO);

    /**
     * 查找部门树结构
     * @return
     */
    List<SysDepartTreeDTO> findTree(boolean self);

    /**
     * 拖拽排序
     * @param reqDTO
     */
    void sort(DepartSortReqDTO reqDTO);

    /**
     * 同步部门信息
     * @param str 以逗号隔开的部门
     * @return
     */
    String syncDepart(String str);

    /**
     * 删除部门
     * @param ids
     */
    void delete(List<String> ids);
}
