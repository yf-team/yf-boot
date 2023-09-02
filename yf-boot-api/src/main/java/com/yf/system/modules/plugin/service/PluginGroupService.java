package com.yf.system.modules.plugin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yf.base.api.api.dto.PagingReqDTO;
import com.yf.system.modules.plugin.dto.PluginGroupDTO;
import com.yf.system.modules.plugin.entity.PluginGroup;

import java.util.List;

/**
* <p>
* 插件分组业务接口类
* </p>
*
* @author 聪明笨狗
* @since 2022-09-05 10:05
*/
public interface PluginGroupService extends IService<PluginGroup> {

    /**
    * 分页查询数据
    * @param reqDTO
    * @return
    */
    IPage<PluginGroupDTO> paging(PagingReqDTO<PluginGroupDTO> reqDTO);

    /**
    * 添加或保存
    * @param reqDTO
    * @return
    */
    void save(PluginGroupDTO reqDTO);

    /**
    * 批量删除
    * @param ids
    * @return
    */
    void delete(List<String> ids);

    /**
    * 查找详情
    * @param id
    * @return
    */
    PluginGroupDTO detail(String id);

    /**
    * 查找列表
    * @param reqDTO
    * @return
    */
    List<PluginGroupDTO> list(PluginGroupDTO reqDTO);
}
