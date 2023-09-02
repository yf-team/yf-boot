package com.yf.system.modules.role.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yf.system.modules.role.dto.SysRoleDTO;
import com.yf.system.modules.role.entity.SysRole;
import com.yf.base.api.api.dto.PagingReqDTO;

import java.util.List;

/**
* <p>
* 角色业务类
* </p>
*
* @author 聪明笨狗
* @since 2020-04-13 16:57
*/
public interface SysRoleService extends IService<SysRole> {

    /**
    * 分页查询数据
    * @param reqDTO
    * @return
    */
    IPage<SysRoleDTO> paging(PagingReqDTO<SysRoleDTO> reqDTO);

    /**
     * 查找最大级别的角色
     * @param ids
     * @return
     */
    int findMaxLevel(List<String> ids);

    /**
     * 保存
     * @param reqDTO
     */
    void save(SysRoleDTO reqDTO);

    /**
     * 删除列表
     * @param ids
     */
    void delete(List<String> ids);
}
