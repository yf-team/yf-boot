package com.yf.system.modules.dict.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yf.base.api.api.dto.PagingReqDTO;
import com.yf.system.modules.dict.dto.SysDicDTO;
import com.yf.system.modules.dict.entity.SysDic;

import java.util.List;

/**
* <p>
* 分类字典业务类
* </p>
*
* @author 聪明笨狗
* @since 2020-12-01 14:00
*/
public interface SysDicService extends IService<SysDic> {

    /**
    * 分页查询数据
    * @param reqDTO
    * @return
    */
    IPage<SysDicDTO> paging(PagingReqDTO<SysDicDTO> reqDTO);

    /**
     * 保存字典
     * @param reqDTO
     */
    void save(SysDicDTO reqDTO);

    /**
     * 批量删除
     * @param ids
     */
    void delete(List<String> ids);


}
