package com.yf.ability.shiro.aop;


import com.yf.ability.Constant;
import com.yf.ability.shiro.jwt.JwtToken;
import com.yf.base.api.api.ApiError;
import com.yf.base.api.api.ApiRest;
import com.yf.base.utils.jackson.JsonHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 鉴权登录拦截器
 * @author bool
 */
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {

	/**
	 * 跨域请求
	 */
	private static final String CROSS_OPTIONS = "OPTIONS";

	/**
	 * 特殊授权登录
	 */
	private static final String URL_EXCLUDE = "/api/connect";
	private static final String URL_SYNC = "/api/open/user/sync";

	/**
	 * 执行登录认证
	 * @param servletRequest
	 * @param servletResponse
	 * @param mappedValue
	 * @return
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue) {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		//这几句代码是关键
		if (CROSS_OPTIONS.equals(request.getMethod())){
			response.setStatus(HttpStatus.SC_NO_CONTENT);
			log.info("++++++++++放行options请求");
			return true;
		}

		String url = request.getRequestURI();
		boolean login = this.executeLogin(servletRequest, servletResponse);

		// 三方登录只是做登录尝试，即使未登录也通过
		if(url.startsWith(URL_EXCLUDE) || url.startsWith(URL_SYNC)){
			return true;
		}

		return login;
	}


	@Override
	protected boolean executeLogin(ServletRequest servletRequest, ServletResponse servletResponse) {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String token = request.getHeader(Constant.TOKEN);

		// 尝试从cookie中获取
		if(StringUtils.isBlank(token)){
			Cookie[] cookies = request.getCookies();
			if(cookies!=null && cookies.length>0){
				for (Cookie cookie: cookies){
					if(Constant.TOKEN.equals(cookie.getName())){
						token = cookie.getValue();
					}
				}
			}
		}

		if(!StringUtils.isBlank(token)){
			JwtToken jwtToken = new JwtToken(token);
			// 提交给realm进行登入，如果错误他会抛出异常并被捕获
			try {
				getSubject(request, response).login(jwtToken);
				return true;
			}catch (Exception e){
				// 捕获异常并返回false即可，下一步给onAccessDenied去处理
				return false;
			}
		}

		return false;
	}

	/**
	 * 执行授权错误时的方法
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setCharacterEncoding("UTF-8");
		httpServletResponse.setContentType("application/json");
		httpServletResponse.setStatus(200);

		// 写入错误信息
		ApiRest apiRest = new ApiRest(ApiError.ERROR_10010002);
		httpServletResponse.getWriter().print(JsonHelper.toJson(apiRest));
		return false;
	}

}
