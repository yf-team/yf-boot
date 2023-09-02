package com.yf.system.modules.role.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.yf.base.api.api.dto.PagingReqDTO;
import com.yf.base.api.exception.ServiceException;
import com.yf.base.utils.BeanMapper;
import com.yf.base.utils.jackson.JsonHelper;
import com.yf.system.modules.role.dto.SysRoleDTO;
import com.yf.system.modules.role.entity.SysRole;
import com.yf.system.modules.role.mapper.SysRoleMapper;
import com.yf.system.modules.role.service.SysRoleService;
import com.yf.system.modules.user.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <p>
* 语言设置 服务实现类
* </p>
*
* @author 聪明笨狗
* @since 2020-04-13 16:57
*/
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {


    @Override
    public IPage<SysRoleDTO> paging(PagingReqDTO<SysRoleDTO> reqDTO) {

        //创建分页对象
        IPage<SysRole> query = reqDTO.toPage();

        //查询条件
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();


        SysRoleDTO params = reqDTO.getParams();
        if(params!=null && StringUtils.isNotBlank(params.getRoleName())){
            wrapper.lambda().like(SysRole::getRoleName, params.getRoleName());
        }

        //获得数据
        IPage<SysRole> page = this.page(query, wrapper);
        //转换结果
        IPage<SysRoleDTO> pageData = JsonHelper.parseObject(page, new TypeReference<Page<SysRoleDTO>>(){});
        return pageData;
     }


    @Override
    public int findMaxLevel(List<String> ids) {
        //查询条件
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(SysRole::getId, ids);

        List<SysRole> list = this.list(wrapper);
        int level = 0;

        for (SysRole role: list){
            if(level < role.getRoleLevel()){
                level = role.getRoleLevel();
            }
        }

        return level;
    }

    @Override
    public void save(SysRoleDTO reqDTO) {

        int myLevel = UserUtils.getRoleLevel();

        if(reqDTO.getRoleLevel() >= myLevel){
            throw new ServiceException("只能添加或修改角色级别小于"+myLevel+"的角色！");
        }

        //复制参数
        SysRole entity = new SysRole();

        if(!StringUtils.isBlank(reqDTO.getId())){
            // 查找原有的，如果原来的数据高于当前，则不能做任何操作
            SysRole ori = this.getById(reqDTO.getId());
            if(ori!=null){
                int level = ori.getRoleLevel();
                if(myLevel <= level){
                    throw new ServiceException("您不能修改级别高于"+myLevel+"的角色！");
                }
            }
        }

        BeanMapper.copy(reqDTO, entity);
        this.saveOrUpdate(entity);
    }

    @Override
    public void delete(List<String> ids) {
        //查询条件
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .in(SysRole::getId, ids)
                .lt(SysRole::getRoleLevel, UserUtils.getRoleLevel());

        long count = this.count(wrapper);
        if(count < ids.size()){
            throw new ServiceException("被删除的角色级别必须小于"+UserUtils.getRoleLevel());
        }

        this.removeByIds(ids);
    }
}
