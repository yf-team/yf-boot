package com.pw.ability.shiro;


import com.pw.ability.shiro.jwt.JwtToken;
import com.pw.ability.shiro.jwt.JwtUtils;
import com.pw.system.modules.menu.service.SysMenuService;
import com.pw.system.modules.user.dto.response.SysUserLoginDTO;
import com.pw.system.modules.user.service.SysUserRoleService;
import com.pw.system.modules.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

/**
 * 用户登录鉴权和获取用户授权
 * @author bool
 */
@Component
@Slf4j
public class MyShiroRealm extends AuthorizingRealm {

	@Autowired
	@Lazy
	private SysUserService sysUserService;

	@Autowired
	@Lazy
	private SysUserRoleService sysUserRoleService;

	@Autowired
	@Lazy
	private SysMenuService sysMenuService;


	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof JwtToken;
	}


	/**
	 * 详细授权认证
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {


		if (principals != null) {

			// 获取用户信息
			SysUserLoginDTO user = (SysUserLoginDTO) principals.getPrimaryPrincipal();

			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

			// 查找用户角色
			List<String> roles = user.getRoles();
			info.setRoles(new HashSet<>(roles));

			// 查找权限明细
			List<String> permissions = user.getPermissions();
			info.addStringPermissions(permissions);

			return info;
		}

		throw new AuthenticationException("登录信息不存在！");
	}

	/**
	 * 校验用户的账号密码是否正确
	 * @param auth
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {


		log.info("++++++++++开始校验详细权限2222+++");

		String token = (String) auth.getCredentials();
		if (token == null) {
			throw new AuthenticationException("token为空!");
		}
		// 校验token有效性
		SysUserLoginDTO user = this.checkToken(token);
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, token, getName());

		return info;
	}


	/**
	 * 校验Token的有效性
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 */
	public SysUserLoginDTO checkToken(String token) throws AuthenticationException {
		
		try {
			JwtUtils.getUsername(token);
		} catch (Exception e) {
			throw new AuthenticationException("无效的token");
		}

		// 查找登录用户对象
		SysUserLoginDTO user = sysUserService.token(token);

		// 校验token是否超时
		if (JwtUtils.expired(token)) {
			throw new AuthenticationException("会话失效，请重新登录！");
		}

		return user;
	}

	/**
	 * 清除当前用户的权限认证缓存
	 * @param principals
	 */
	@Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

}
