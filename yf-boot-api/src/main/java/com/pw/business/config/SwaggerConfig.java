package com.pw.business.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * Swagger配置
 * @author bool
 * @date 2020/8/19 20:53
 */
@Configuration
@EnableSwagger2
@ConfigurationProperties(prefix = "swagger")
public class SwaggerConfig {


    @Bean
    public Docket commonApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("公共能力模块")
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.ant("/api/common/**"))
                .build()
                .securitySchemes(Collections.singletonList(securityScheme()));
    }



    @Bean
    public Docket sysApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("系统模块接口")
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.ant("/api/sys/**"))
                .build()
                .securitySchemes(Collections.singletonList(securityScheme()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("管理系统接口")
                .description("管理系统接口")
                .contact(new Contact("PieWay", "https://www.pieway.com", "dev@pieway.com"))
                .version("1.0.0")
                .build();
    }


    @Bean
    SecurityScheme securityScheme() {
        return new ApiKey("token", "token", "header");
    }

}
