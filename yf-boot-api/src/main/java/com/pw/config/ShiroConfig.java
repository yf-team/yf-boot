package com.pw.config;

import com.pw.ability.shiro.CNFilterFactoryBean;
import com.pw.ability.shiro.MyShiroRealm;
import com.pw.ability.shiro.aop.JwtFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Shiro配置类
 * @author bool
 */
@Slf4j
@Configuration
public class ShiroConfig {

    @Value("${spring.redis.port}")
    private String redisPort;

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.password}")
    private String redisPass;

	@Value("${spring.redis.database}")
	private Integer database;

	/**
	 * Filter Chain定义说明
	 *
	 * 1、一个URL可以配置多个Filter，使用逗号分隔
	 * 2、当设置多个过滤器时，全部验证通过，才视为通过
	 * 3、部分过滤器可指定参数，如perms，roles
	 */
	@Bean("shiroFilterFactoryBean")
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new CNFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		// 拦截器
		Map<String, String> map = new LinkedHashMap<>();

		// 用户登录注册相关
		map.put("/api/sys/user/login", "anon");

		map.put("/api/sys/user/reg", "anon");
		map.put("/api/sys/user/reset-pass", "anon");
		map.put("/api/sys/user/sync-login", "anon");
		map.put("/api/sys/depart/tree-select", "anon");

		// 验证码相关
		map.put("/api/common/captcha/**", "anon");
		map.put("/api/common/sms/**", "anon");

		// 通用系统配置信息
		map.put("/api/sys/config/detail", "anon");

		// 文件读取
		map.put("/upload/file/**", "anon");

		map.put("/", "anon");
		map.put("/v2/**", "anon");
		map.put("/doc.html", "anon");
		map.put("/**/*.js", "anon");
		map.put("/**/*.css", "anon");
		map.put("/**/*.html", "anon");
		map.put("/**/*.svg", "anon");
		map.put("/**/*.pdf", "anon");
		map.put("/**/*.jpg", "anon");
		map.put("/**/*.png", "anon");
		map.put("/**/*.ico", "anon");
		map.put("/**/*.txt", "anon");

		// 字体
		map.put("/**/*.ttf", "anon");
		map.put("/**/*.woff", "anon");
		map.put("/**/*.woff2", "anon");
		map.put("/druid/**", "anon");
		map.put("/swagger-ui.html", "anon");
		map.put("/swagger**/**", "anon");
		map.put("/webjars/**", "anon");

		// 添加自己的过滤器并且取名为jwt
		Map<String, Filter> filterMap = new HashMap<>(1);
		filterMap.put("jwt", new JwtFilter());
		shiroFilterFactoryBean.setFilters(filterMap);
		map.put("/**", "jwt");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
		return shiroFilterFactoryBean;
	}

	@Bean("securityManager")
	public DefaultWebSecurityManager securityManager(MyShiroRealm myRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
		DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
		defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
		subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
		securityManager.setSubjectDAO(subjectDAO);
        //自定义缓存实现,使用redis
        securityManager.setCacheManager(redisCacheManager());
		securityManager.setRealm(myRealm);
		return securityManager;
	}

	/**
	 * 下面的代码是添加注解支持
	 * @return
	 */
	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
		defaultAdvisorAutoProxyCreator.setUsePrefix(true);
		return defaultAdvisorAutoProxyCreator;
	}

	@Bean
	public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisCacheManager redisCacheManager() {
        log.info("++++++++++初始化缓存管理器:RedisCacheManager");
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        //redis中针对不同用户缓存(此处的id需要对应user实体中的id字段,用于唯一标识)
        redisCacheManager.setPrincipalIdFieldName("id");
        //用户权限信息缓存时间
        redisCacheManager.setExpire(Integer.MAX_VALUE);
        return redisCacheManager;
    }

    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Bean
    public RedisManager redisManager() {
        log.info("++++++++++初始化权限缓存:RedisManager:" + redisHost + ":" + redisPort);
		RedisManager redisManager = new RedisManager();
		redisManager.setHost(redisHost + ":"  + redisPort);
		redisManager.setDatabase(database);
		redisManager.setTimeout(0);
		if (!StringUtils.isEmpty(redisPass)) {
			redisManager.setPassword(redisPass);
		}
		return redisManager;
    }

}
