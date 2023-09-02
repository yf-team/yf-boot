package com.yf.system.modules.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.yf.base.api.api.dto.PagingReqDTO;
import com.yf.base.api.exception.ServiceException;
import com.yf.base.utils.jackson.JsonHelper;
import com.yf.system.modules.user.dto.SysUserBindDTO;
import com.yf.system.modules.user.entity.SysUserBind;
import com.yf.system.modules.user.enums.LoginType;
import com.yf.system.modules.user.mapper.SysUserBindMapper;
import com.yf.system.modules.user.service.SysUserBindService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

/**
* <p>
* 登录绑定业务实现类
* </p>
*
* @author 聪明笨狗
* @since 2021-08-02 14:49
*/
@Service
public class SysUserBindServiceImpl extends ServiceImpl<SysUserBindMapper, SysUserBind> implements SysUserBindService {


    @Override
    public IPage<SysUserBindDTO> paging(PagingReqDTO<SysUserBindDTO> reqDTO) {

        //查询条件
        QueryWrapper<SysUserBind> wrapper = new QueryWrapper<>();

        // 请求参数
        SysUserBindDTO params = reqDTO.getParams();

        //获得数据
        IPage<SysUserBind> page = this.page(reqDTO.toPage(), wrapper);
        //转换结果
        IPage<SysUserBindDTO> pageData = JsonHelper.parseObject(page, new TypeReference<Page<SysUserBindDTO>>(){});
        return pageData;
    }

    @Override
    public void save(boolean clear, String userId, String loginType, String openId) {



        // 先移除
        if(clear){
            QueryWrapper<SysUserBind> wrapper = new QueryWrapper<>();
            wrapper.lambda()
                    .eq(SysUserBind::getUserId, userId)
                    .eq(SysUserBind::getLoginType, loginType);
            this.remove(wrapper);
        }

        // 查找绑定信息
        String currentId = this.findBind(loginType, openId);

        if(!StringUtils.isBlank(currentId)){
            if(LoginType.MOBILE.equals(loginType)){
                throw new ServiceException(MessageFormat.format("手机号码{0}已绑定用户！", openId));
            }else{
                throw new ServiceException(MessageFormat.format("{0}已绑定用户！", openId));
            }
        }

        SysUserBind bind = new SysUserBind();
        bind.setUserId(userId);
        bind.setLoginType(loginType);
        bind.setOpenId(openId);
        this.save(bind);
    }

    @Override
    public void delete(List<String> ids){
        //批量删除
        this.removeByIds(ids);
    }


    @Override
    public String findBind(String loginType, String openId) {
        //分页查询并转换
        QueryWrapper<SysUserBind> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(SysUserBind::getLoginType, loginType)
                .eq(SysUserBind::getOpenId, openId);
        SysUserBind bind = this.getOne(wrapper, false);
        if(bind!=null){
            return bind.getUserId();
        }

        return null;
    }

    @Override
    public boolean hasBind(String loginType, String openId) {
        //分页查询并转换
        QueryWrapper<SysUserBind> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(SysUserBind::getLoginType, loginType)
                .eq(SysUserBind::getOpenId, openId);

        return this.count(wrapper) > 0;
    }

}
