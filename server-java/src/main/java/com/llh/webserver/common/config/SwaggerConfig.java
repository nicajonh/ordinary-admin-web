package com.llh.webserver.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * SwaggerConfig
 * Swagger文档配置
 * <p>
 * CreatedAt: 2020-04-18 11:55
 *
 * @author llh
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value("${swagger2.enable:false}")
    private boolean enable;

    @Bean
    public Docket createRestApi() {
        List<Parameter> pars = new ArrayList<>();
        ParameterBuilder tokenPar = new ParameterBuilder();
        ParameterBuilder refreshTokenPar = new ParameterBuilder();
        tokenPar.name("authorization")
            .description("swagger测试用(模拟authorization传入)非必填 header")
            .modelRef(new ModelRef("string")).parameterType("header").required(false);
        refreshTokenPar.name("refresh_token")
            .description("swagger测试用(模拟刷新token传入)非必填 header")
            .modelRef(new ModelRef("string")).parameterType("header").required(false);

        pars.add(tokenPar.build());
        pars.add(refreshTokenPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.llh.webserver.controller"))
            .paths(PathSelectors.any())
            .build()
            .globalOperationParameters(pars)
            .enable(enable);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("ordinary-admin-web")
            .description("普通的后台管理系统")
            .termsOfServiceUrl("")
            .version("1.0")
            .build();
    }
}
