package com.yf.ability.shiro.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yf.base.utils.file.MD5Util;

import java.util.Calendar;
import java.util.Date;

/**
 * JWT工具类
 * @author bool
 */
public class JwtUtils {


	/**
	 * 有效期24小时
	 */
	private static final int EXPIRE_TIME = 24;

	/**
	 * 使用固定的解密秘钥
	 */
	private static final String SECRET = "PisyGFJhgzrctMOofvaHLuiNFOmktedw";

	/**
	 * 校验是否正确
	 * @param token
	 * @param username
	 * @return
	 */
	public static boolean verify(String token, String username) {
		try {
			// 根据密码生成JWT效验器
			Algorithm algorithm = Algorithm.HMAC256(encryptSecret(username));
			JWTVerifier verifier = JWT.require(algorithm)
					.withClaim("username", username)
					.build();
			// 效验TOKEN
			verifier.verify(token);
			return true;
		} catch (Exception exception) {
			return false;
		}
	}





	/**
	 * 从Token中解密获得用户名
	 * @param token
	 * @return
	 */
	public static String getUsername(String token) throws Exception {
		DecodedJWT jwt = JWT.decode(token);
		return jwt.getClaim("username").asString();
	}

	/**
	 * 生成JWT Token字符串
	 * @param username
	 * @return
	 */
	public static String sign(String username) {
		Calendar cl = Calendar.getInstance();
		cl.setTimeInMillis(System.currentTimeMillis());
		cl.add(Calendar.HOUR, EXPIRE_TIME);

		Algorithm algorithm = Algorithm.HMAC256(encryptSecret(username));
		// 附带username信息
		return JWT.create()
				.withClaim("username", username)
				.withExpiresAt(cl.getTime()).sign(algorithm);
	}

	/**
	 * 根据用户名和秘钥，生成一个新的秘钥，用于JWT加强一些安全性
	 * @param userName
	 * @return
	 */
	private static String encryptSecret(String userName){
		return  MD5Util.MD5(userName + "&" + SECRET);
	}


	/**
	 * 判断Token是否到期
	 * @param token
	 * @return
	 */
	public static boolean expired(String token) {
		DecodedJWT jwt = JWT.decode(token);
		return  new Date().after(jwt.getExpiresAt());
	}
}
