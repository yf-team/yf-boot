package com.yf.system.modules.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yf.ability.Constant;
import com.yf.ability.captcha.service.CaptchaService;
import com.yf.ability.redis.service.RedisService;
import com.yf.ability.shiro.dto.SysUserLoginDTO;
import com.yf.ability.shiro.jwt.JwtUtils;
import com.yf.ability.shiro.service.ShiroUserService;
import com.yf.base.api.api.ApiError;
import com.yf.base.api.api.dto.PagingReqDTO;
import com.yf.base.api.exception.ServiceException;
import com.yf.base.utils.BeanMapper;
import com.yf.base.utils.passwd.PassHandler;
import com.yf.base.utils.passwd.PassInfo;
import com.yf.base.utils.CacheKey;
import com.yf.base.utils.jackson.JsonHelper;
import com.yf.system.modules.config.enums.FuncSwitch;
import com.yf.system.modules.config.service.CfgSwitchService;
import com.yf.system.modules.menu.service.SysMenuService;
import com.yf.system.modules.role.entity.SysRole;
import com.yf.system.modules.user.UserUtils;
import com.yf.system.modules.user.dto.request.*;
import com.yf.system.modules.user.dto.response.UserListRespDTO;
import com.yf.system.modules.user.entity.SysUser;
import com.yf.system.modules.user.enums.LoginType;
import com.yf.system.modules.user.enums.SysRoleId;
import com.yf.system.modules.user.enums.UserState;
import com.yf.system.modules.user.mapper.SysUserMapper;
import com.yf.system.modules.user.service.SysUserBindService;
import com.yf.system.modules.user.service.SysUserRoleService;
import com.yf.system.modules.user.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 语言设置 服务实现类
 * </p>
 *
 * @author 聪明笨狗
 * @since 2020-04-13 16:57
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService, ShiroUserService {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private SysUserBindService sysUserBindService;

    @Autowired
    private CfgSwitchService cfgSwitchService;

    @Autowired
    private SysMenuService sysMenuService;


    @Override
    public SysUserSaveReqDTO detail(String id) {

        // 基础信息复制
        SysUser user = this.getById(id);
        SysUserSaveReqDTO respDTO = new SysUserSaveReqDTO();
        BeanMapper.copy(user, respDTO);

        // 角色是要
        List<SysRole> roleList = sysUserRoleService.listRoles(user.getId());
        List<String> roles = new ArrayList<>();
        for(SysRole role: roleList){
            roles.add(role.getId());
        }
        respDTO.setRoles(roles);
        return respDTO;
    }

    @Override
    public IPage<UserListRespDTO> paging(PagingReqDTO<SysUserQueryReqDTO> reqDTO) {

        //创建分页对象
        Page page = reqDTO.toPage();

        //转换结果
        IPage<UserListRespDTO> pageData = baseMapper.paging(page, reqDTO.getParams());
        return pageData;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<String> ids) {

        int count = sysUserRoleService.countWithLevel(ids, UserUtils.getRoleLevel());

        if(count < ids.size()){
            throw new ServiceException("删除错误，可能存在越权操作！");
        }

        if(ids.contains(UserUtils.getUserId())){
            throw new ServiceException("您不可以删除自己的账号！");
        }

        // 移除数据
        this.removeByIds(ids);
    }

    @Override
    public SysUserLoginDTO login(SysUserLoginReqDTO reqDTO) {

        // 校验图形验证码
        if (!StringUtils.isBlank(reqDTO.getCaptchaKey())) {
            boolean check = captchaService.checkCaptcha(reqDTO.getCaptchaKey(), reqDTO.getCaptchaValue());
            if (!check) {
                throw new ServiceException("图形验证码不正确或已失效！");
            }
        }

        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysUser::getUserName, reqDTO.getUserName());
        SysUser user = this.getOne(wrapper, false);

        // 校验用户状态&密码
        return this.checkAndLogin(user, reqDTO.getPassword());
    }


    /**
     * 用户登录校验
     *
     * @param user
     */
    private SysUserLoginDTO checkAndLogin(SysUser user, String password) {

        if (user == null) {
            throw new ServiceException(ApiError.ERROR_90010001);
        }

        // 被禁用
        if (UserState.DISABLED.equals(user.getState())) {
            throw new ServiceException(ApiError.ERROR_90010005);
        }

        // 待审核
        if (UserState.AUDIT.equals(user.getState())) {
            throw new ServiceException(ApiError.ERROR_90010006);
        }

        if (!StringUtils.isBlank(password)) {
            boolean pass = PassHandler.checkPass(password, user.getSalt(), user.getPassword());
            if (!pass) {
                throw new ServiceException(ApiError.ERROR_90010002);
            }
        }

        return this.setToken(user);
    }

    @Override
    public List<String> permissions(String userId) {
        return sysUserRoleService.findUserPermission(userId);
    }

    @Override
    public List<String> roles(String userId) {
        return sysUserRoleService.listRoleIds(userId);
    }

    @Override
    public SysUserLoginDTO token(String token) {

        // 获得会话
        String username;
        try {
            username = JwtUtils.getUsername(token);
        } catch (Exception e) {
            throw new ServiceException("会话失效，请重新登录！");
        }

        Map<String,Object> json = redisService.getJson(Constant.USER_NAME_KEY + username);
        if (json == null) {
            throw new ServiceException(ApiError.ERROR_10010002);
        }

        SysUserLoginDTO respDTO = JsonHelper.parseObject(json, SysUserLoginDTO.class);

//        // 是否T下线
//        boolean tick = cfgSwitchService.isOn(FuncSwitch.LOGIN_TICK);
//        if (tick) {
//            if (!token.equals(respDTO.getToken())) {
//                throw new ServiceException("您的账号在其他地方登录了！");
//            }
//        }

//        // 填充积分信息
//        SysUser user = this.getById(respDTO.getId());
//
//        if (user == null) {
//            // 可能是脏的用户数据
//            throw new ServiceException(ApiError.ERROR_10010002);
//        }
//
//
//        respDTO.setPoints(user.getPoints());
//        respDTO.setRealName(user.getRealName());
//        respDTO.setIdCard(user.getIdCard());
//        respDTO.setEmail(user.getEmail());
//        respDTO.setMobile(user.getMobile());
//        respDTO.setAvatar(user.getAvatar());
//        respDTO.setFace(user.getFace());
        return respDTO;
    }

    @CacheEvict(value = CacheKey.TOKEN, key = "#token")
    @Override
    public void logout(String token) {

        // 遵循T下线原则
        boolean tick = cfgSwitchService.isOn(FuncSwitch.LOGIN_TICK);
        if (tick) {
            try {
                String username = JwtUtils.getUsername(token);
                String [] keys = new String[]{Constant.USER_NAME_KEY + username};
                redisService.del(keys);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(SysUserUpdateReqDTO reqDTO) {

        // 查找旧的用户信息
        SysUser u1 = this.getById(UserUtils.getUserId());

        SysUser user = new SysUser();
        user.setId(UserUtils.getUserId());
        BeanMapper.copy(reqDTO, user);

        // 修改标识
        boolean reLogin = false;

        // 修改密码
        String password = reqDTO.getPassword();
        if (!StringUtils.isBlank(password)) {
            PassInfo passInfo = PassHandler.buildPassword(password);
            user.setPassword(passInfo.getPassword());
            user.setSalt(passInfo.getSalt());
            reLogin = true;
        }



        // 重新登录
        if (reLogin) {
            // 退出登录
            String[] keys = new String[]{Constant.USER_NAME_KEY + user.getUserName()};
            redisService.del(keys);
        }

        this.updateById(user);
        this.setToken(user);
    }



    @CacheEvict(value = CacheKey.MENU, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(SysUserSaveReqDTO reqDTO) {

        List<String> roles = reqDTO.getRoles();

        if (CollectionUtils.isEmpty(roles)) {
            throw new ServiceException(ApiError.ERROR_90010003);
        }

        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysUser::getUserName, reqDTO.getUserName());
        if (!StringUtils.isBlank(reqDTO.getId())) {
            wrapper.lambda().ne(SysUser::getId, reqDTO.getId());
        }


        long count = this.count(wrapper);
        if (count > 0) {
            throw new ServiceException("用户名不能重复！");
        }


        // 保存基本信息
        SysUser user;

        // 添加模式
        if (StringUtils.isBlank(reqDTO.getId())) {
            user = new SysUser();
            BeanMapper.copy(reqDTO, user);
            user.setId(IdWorker.getIdStr());
        } else {
            user = this.getById(reqDTO.getId());
            BeanMapper.copy(reqDTO, user);
        }

        // 级别
        int level = sysUserRoleService.findMaxLevel(reqDTO.getId());
        if(level > UserUtils.getRoleLevel()){
            throw new ServiceException("越级操作，不能操作等级高的用户！");
        }


        // 修改密码
        if (!StringUtils.isBlank(reqDTO.getPassword())) {
            PassInfo pass = PassHandler.buildPassword(reqDTO.getPassword());
            user.setPassword(pass.getPassword());
            user.setSalt(pass.getSalt());
        }

        // 保存角色信息
        sysUserRoleService.saveRoles(user.getId(), roles, true);

        // 保存绑定关系
        this.saveOrUpdate(user);

        // 更新手机绑定
        if(!StringUtils.isBlank(user.getMobile())){
            sysUserBindService.save(true, user.getId(), LoginType.MOBILE, user.getMobile());
        }

    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysUserLoginDTO reg(UserRegReqDTO reqDTO) {


        boolean check = captchaService.checkCaptcha(reqDTO.getCaptchaKey(), reqDTO.getCaptchaValue());
        if (!check) {
            throw new ServiceException("图形验证码不正确或已失效！");
        }


        // 功能开关
        boolean on = cfgSwitchService.isOn(FuncSwitch.USER_REG);
        if(!on){
            throw new ServiceException("管理员未开启用户注册！");
        }


        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .select(SysUser::getId)
                .eq(SysUser::getUserName, reqDTO.getUserName());

        // 用户名即为手机号
        boolean exists = this.count(wrapper) > 0;
        if (exists) {
            throw new ServiceException("用户名已存在，换一个吧！！");
        }

        return this.saveAndLogin(
                null,
                reqDTO.getUserName(),
                reqDTO.getDeptCode(),
                reqDTO.getRealName(),
                null,
                null,
                "",
                reqDTO.getPassword());
    }


    /**
     * 保存用户并自动登录
     *
     * @param userName
     * @param deptCode
     * @param realName
     * @param mobile
     * @param avatar
     * @param password
     * @return
     */
    private SysUserLoginDTO saveAndLogin(String userId, String userName, String deptCode, String realName, String role, String mobile, String avatar, String password) {

        // 保存用户
        SysUser user = new SysUser();

        // 指定用户ID的
        if (!StringUtils.isBlank(userId)) {
            user.setId(userId);
        } else {
            user.setId(IdWorker.getIdStr());
        }

        // 指定部门
        boolean on = cfgSwitchService.isOn(FuncSwitch.USER_DEPT_TYPE);
        if(on){
            deptCode = cfgSwitchService.val(FuncSwitch.USER_DEPT_CODE);
        }

        // 需要审核
        boolean audit = cfgSwitchService.isOn(FuncSwitch.USER_AUDIT);
        if(audit){
            user.setState(UserState.AUDIT);
        }else {
            user.setState(UserState.NORMAL);
        }

        user.setUserName(userName);
        user.setRealName(realName);
        user.setDeptCode(deptCode);
        user.setMobile(mobile);
        user.setAvatar(avatar);
        PassInfo passInfo = PassHandler.buildPassword(password);
        user.setPassword(passInfo.getPassword());
        user.setSalt(passInfo.getSalt());

        // 保存角色
        List<String> roleList = new ArrayList<>();
        if (!StringUtils.isBlank(role)) {
            roleList.add(role);
        } else {
            // 默认用户
            roleList.add(SysRoleId.USER);
        }


        // 保存角色
        sysUserRoleService.saveRoles(user.getId(), roleList, false);

        // 保存用户
        this.save(user);

        // 有手机自动添加绑定关系
        if(!StringUtils.isBlank(mobile)){
            sysUserBindService.save(false, user.getId(), LoginType.MOBILE, mobile);
        }

        return this.setToken(user);
    }



    /**
     * 保存会话信息
     *
     * @param user
     * @return
     */
    private SysUserLoginDTO setToken(SysUser user) {

        // 获取一个用户登录的信息
        String key = Constant.USER_NAME_KEY + user.getUserName();
        String json = redisService.getString(key);
        if (!StringUtils.isBlank(json)) {
            // 删除旧的会话
            redisService.del(key);
        }

        SysUserLoginDTO respDTO = new SysUserLoginDTO();
        BeanMapper.copy(user, respDTO);

        // 正常状态才登录
        if(UserState.NORMAL.equals(user.getState())){

            // 根据用户生成Token
            String token = JwtUtils.sign(user.getUserName());
            respDTO.setToken(token);

            // 添加角色信息
            this.fillRoleData(respDTO);

            // 权限表，用于前端控制按钮
            List<String> permissions = sysMenuService.listPermissionByRoles(respDTO.getRoles());
            respDTO.setPermissions(permissions);


            // 保存如Redis
            redisService.set(key, JsonHelper.toJson(respDTO));
        }

        return respDTO;

    }


    /**
     * 追加用户角色信息
     *
     * @param respDTO
     */
    private void fillRoleData(SysUserLoginDTO respDTO) {

        // 角色是要
        List<SysRole> roleList = sysUserRoleService.listRoles(respDTO.getId());
        // 角色级别
        Integer roleLevel = 0;
        // 数据权限1最小：查看自己的数据
        Integer dataScope = 1;

        List<String> roleIds = new ArrayList<>();
        for (SysRole role : roleList) {
            // 角色ID
            roleIds.add(role.getId());
            // 替换大的权限
            if (dataScope < role.getDataScope()) {
                dataScope = role.getDataScope();
            }
            // 权限级别
            if (roleLevel < role.getRoleLevel()) {
                roleLevel = role.getRoleLevel();
            }
        }
        respDTO.setRoleLevel(roleLevel);
        respDTO.setDataScope(dataScope);
        respDTO.setRoles(roleIds);
    }
}
