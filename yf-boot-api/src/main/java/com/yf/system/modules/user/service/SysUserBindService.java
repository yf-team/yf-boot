package com.yf.system.modules.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yf.base.api.api.dto.PagingReqDTO;
import com.yf.system.modules.user.dto.SysUserBindDTO;
import com.yf.system.modules.user.entity.SysUserBind;

import java.util.List;

/**
* <p>
* 登录绑定业务接口类
* </p>
*
* @author 聪明笨狗
* @since 2021-08-02 14:49
*/
public interface SysUserBindService extends IService<SysUserBind> {

    /**
    * 分页查询数据
    * @param reqDTO
    * @return
    */
    IPage<SysUserBindDTO> paging(PagingReqDTO<SysUserBindDTO> reqDTO);

    /**
     * 添加绑定关系
     * @param userId
     * @param loginType
     * @param openId
     */
    void save(boolean clear, String userId, String loginType, String openId);

    /**
    * 批量删除
    * @param ids
    * @return
    */
    void delete(List<String> ids);

    /**
     * 根据绑定信息查找用户ID
     * @param loginType
     * @param openId
     * @return
     */
    String findBind(String loginType, String openId);

    /**
     * 校验是否绑定
     * @param bindType
     * @param openId
     * @return
     */
    boolean hasBind(String bindType, String openId);


}
