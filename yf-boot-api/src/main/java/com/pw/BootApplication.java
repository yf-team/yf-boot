package com.pw;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.pw.ability.desensitize.filter.DesensitizeFilter;
import com.pw.system.aspect.dict.DataDictFilter;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 云帆开发框架
 * @author bool
 */
@Log4j2
@SpringBootApplication
@EnableCaching
public class BootApplication implements WebMvcConfigurer {

	public static void main(String[] args) throws UnknownHostException {

		ConfigurableApplicationContext application = SpringApplication.run(BootApplication.class, args);
		Environment env = application.getEnvironment();
		String ip = InetAddress.getLocalHost().getHostAddress();
		String port = env.getProperty("server.port");
		String path = env.getProperty("server.servlet.context-path");

		// 未配置默认空白
		if(path == null){
			path = "";
		}

		log.info("\n----------------------------------------------------------\n\t" +
				"云帆快速开发框架启动成功，访问路径如下:\n\t" +
				"本地路径: \t\thttp://localhost:" + port + path + "/\n\t" +
				"网络地址: \thttp://" + ip + ":" + port + path + "/\n\t" +
				"API文档: \t\thttp://" + ip + ":" + port + path + "/doc.html\n" +
				"----------------------------------------------------------");
	}


	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		//保留原有converter,把新增fastConverter插入集合头,保证优先级
		converters.add(0, fastConverter());
	}

	/**
	 * FastJson消息转换器
	 *
	 * @return
	 */
	public static HttpMessageConverter fastConverter() {
		// 定义一个convert转换消息的对象
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		// 添加FastJson的配置信息
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		// 数据脱敏及字典翻译
		fastJsonConfig.setSerializeFilters(new DesensitizeFilter(), new DataDictFilter());
		// 默认转换器
		fastJsonConfig.setSerializerFeatures(
				SerializerFeature.PrettyFormat,
				// SerializerFeature.WriteNullNumberAsZero,
				// SerializerFeature.MapSortField,
				//SerializerFeature.WriteNullStringAsEmpty,
				SerializerFeature.DisableCircularReferenceDetect
				//SerializerFeature.WriteDateUseDateFormat,
				//SerializerFeature.WriteNullListAsEmpty
		);
		fastJsonConfig.setCharset(Charset.forName("UTF-8"));


//		// 处理中文乱码问题
//		List<MediaType> fastMediaTypes = new ArrayList<>();
//		fastConverter.setSupportedMediaTypes(fastMediaTypes);
		// 在convert中添加配置信息
		fastConverter.setFastJsonConfig(fastJsonConfig);

		return fastConverter;
	}

}
