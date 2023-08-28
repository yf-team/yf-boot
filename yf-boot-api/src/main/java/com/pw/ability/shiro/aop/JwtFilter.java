package com.pw.ability.shiro.aop;


import com.alibaba.fastjson.JSON;
import com.pw.base.api.api.ApiError;
import com.pw.base.api.api.ApiRest;
import com.pw.ability.Constant;
import com.pw.ability.shiro.jwt.JwtToken;
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

		System.out.println("++++网页授权....");

		//这几句代码是关键
		if (CROSS_OPTIONS.equals(request.getMethod())){
			response.setStatus(HttpStatus.SC_NO_CONTENT);
			log.info("++++++++++放行options请求");
			return true;
		}

		String url = request.getRequestURI();
		boolean login = this.executeLogin(servletRequest, servletResponse);

		// 三方登录只是做登录尝试，即使未登录也通过
		if(url.startsWith(URL_EXCLUDE)){
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
				e.printStackTrace();
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
	protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

		HttpServletResponse response = (HttpServletResponse) servletResponse;
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.setStatus(200);
		// 允许跨域
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type,Content-Length,Authorization,Accept,X-Requested-With,token");
		response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");

		// 写入错误信息
		ApiRest apiRest = new ApiRest(ApiError.ERROR_10010002);
		response.getWriter().print(JSON.toJSONString(apiRest));
		return false;
	}

}
