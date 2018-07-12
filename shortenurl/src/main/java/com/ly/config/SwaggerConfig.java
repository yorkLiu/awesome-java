package com.ly.config;

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
 * Created by yongliu on 7/11/18.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
  
  public Docket createApi(){
//    return new Docket(DocumentationType.SWAGGER_2)
//      .genericModelSubstitutes(DeferredResult.class)
//      .useDefaultResponseMessages(Boolean.FALSE)
//      .forCodeGeneration(Boolean.TRUE)
////      .pathMapping("/")
//      .select()
//      .build()
//      .apiInfo(definedApiInfo());
    
//    return new Docket(DocumentationType.SWAGGER_2)
//      .select()
//      .apis(RequestHandlerSelectors.basePackage("com.ly.web"))
//      .paths(PathSelectors.any())
//      .build();

    return new Docket(DocumentationType.SWAGGER_2)
      .genericModelSubstitutes(DeferredResult.class)
      .useDefaultResponseMessages(false)
      .forCodeGeneration(false)
      .pathMapping("/")
      .select()
      .build()
      .apiInfo(definedApiInfo());
    
  }
  
  private ApiInfo definedApiInfo(){
//    ApiInfo apiInfo = new ApiInfo("URL Short Interface",
//      "URL Short Interface",
//      "0.1",
//      "",
//      "Me",
//      "Licence",
//      "Licence URL"
//      );

    ApiInfo apiInfo = new ApiInfoBuilder().title("URL Short Interface").version("0.1").build();
    
    return apiInfo;
  }
}
