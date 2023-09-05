package com.yf.system.modules.plugin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yf.base.api.api.dto.PagingReqDTO;
import com.yf.system.modules.plugin.dto.PluginDataDTO;
import com.yf.system.modules.plugin.entity.PluginData;

import java.util.List;

/**
* <p>
* 插件信息业务接口类
* </p>
*
* @author 聪明笨狗
* @since 2022-09-05 10:05
*/
public interface PluginDataService extends IService<PluginData> {

    /**
    * 分页查询数据
    * @param reqDTO
    * @return
    */
    IPage<PluginDataDTO> paging(PagingReqDTO<PluginDataDTO> reqDTO);

    /**
    * 添加或保存
    * @param reqDTO
    * @return
    */
    void save(PluginDataDTO reqDTO);

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
    PluginDataDTO detail(String id);

    /**
    * 查找列表
    * @param reqDTO
    * @return
    */
    List<PluginDataDTO> list(PluginDataDTO reqDTO);

    /**
     * 根据插件编码查找配置数据，为JSON字符串
     * @param code
     * @return
     */
    String findConfig(String code);

    /**
     * 查找插件服务类，用于实例化数据
     * @param groupId
     * @return
     */
    String findServiceClazz(String groupId);
}
