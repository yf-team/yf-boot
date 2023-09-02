package com.yf.ability.shiro;


import com.yf.ability.shiro.dto.SysUserLoginDTO;
import com.yf.ability.shiro.jwt.JwtToken;
import com.yf.ability.shiro.jwt.JwtUtils;
import com.yf.ability.shiro.service.ShiroUserService;
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
	private ShiroUserService shiroUserService;


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
		log.info("++++++++++开始校验详细权限");

		System.out.println("++++++++详细权限校验。。。。");

		String userId = null;
		if (principals != null) {
			SysUserLoginDTO user = (SysUserLoginDTO) principals.getPrimaryPrincipal();
			userId = user.getId();
		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

		// 查找用户角色
		List<String> roles = shiroUserService.roles(userId);
		info.setRoles(new HashSet<>(roles));

		// 查找权限明细
		List<String> permissions = shiroUserService.permissions(userId);
		info.addStringPermissions(permissions);

		log.info("++++++++++校验详细权限完成");
		return info;
	}

	/**
	 * 校验用户的账号密码是否正确
	 * @param auth
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
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
		SysUserLoginDTO user = shiroUserService.token(token);

		// 校验token是否超时
		if (JwtUtils.expired(token)) {
			throw new AuthenticationException("登陆失效，请重试登陆!");
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
