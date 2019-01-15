package com.example.demo.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 创建人:连磊
 * 日期: 2018/11/9. 11:13
 * 描述：
 */
@Configuration
@EnableSwagger2
public class MySwagger {

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Springboot利用swagger构建api文档")
                .description("简单优雅的restfun风格")
                .termsOfServiceUrl("https:github.com/springfox/springfox-demos")
                .version("1.0") 
                .build();
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2) 
                /*.genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false) 
                .forCodeGeneration(true)*/
                .apiInfo(apiInfo()) 
                /*.pathMapping("/")*/
                .select() 
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controller"))
                .paths(PathSelectors.any()) 
                .build()
                /*.groupName("用户操作")*/
                ;
    }

    @Bean
    public Docket createRestApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controller.user"))
                .paths(PathSelectors.any())
                .build()
                .groupName("爱的发声")
                ;
    }

}
