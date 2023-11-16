package com.javateam.memberProject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2 // 추가
public class SwaggerConfig {
	
	// 추가
	@Bean
    public Docket api() {
        
    	return new Docket(DocumentationType.SWAGGER_2)
    			 .apiInfo(apiInfo())
                 .select()
                 // .apis(RequestHandlerSelectors.any())
                 .apis(RequestHandlerSelectors.basePackage("com.javateam.memberProject"))
                 // 모든 RequestMapping URL주소 목록 추출
                 .paths(PathSelectors.any())
                 // .paths(PathSelectors.ant("/json**/**")) 
                 // 위의 주소 중 제시된 URL들만 대상으로 추출
                 .build();
    } //
	
	private ApiInfo apiInfo() {
		
        return new ApiInfoBuilder()
            .title("Spring Boot Open REST API Test with Swagger")
            .description("<h2 style='color:orange'>회원 관리 프로젝트 REST Test</h2>")
            .version("1.0")
            .build();
    }

}
