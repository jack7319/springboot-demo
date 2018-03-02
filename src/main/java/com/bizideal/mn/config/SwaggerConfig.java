package com.bizideal.mn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/** 
* @author : liulq
* @date: 创建时间：2017年8月26日 下午3:39:58 
* @version: 1.0   
* @Description:   
*/
@Configuration
@EnableSwagger2 // 启用 Swagger2
public class SwaggerConfig {
	
	// 程序启动后访问http://10.1.0.18:9090/swagger-ui.html

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.bizideal.mn.controller"))
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot中使用Swagger2构建RESTful APIs")
                .description("swagger测试")
                .termsOfServiceUrl("http://www.baidu.com/")
                .version("1.0")
                .build();
    }

}