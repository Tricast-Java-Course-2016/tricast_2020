package com.tricast;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestAttribute;

import com.tricast.controllers.filters.AuthenticationFilter;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class WorkingHoursApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkingHoursApplication.class, args);
	}

    @Bean
    public Docket api() {

        Parameter globalParam =
                new ParameterBuilder().name("Authorization").description("Authorization token")
                        .modelRef(new ModelRef("string")).parameterType("header").required(false).build();

        return new Docket(DocumentationType.SWAGGER_2).ignoredParameterTypes(RequestAttribute.class)
                .globalOperationParameters(Arrays.asList(globalParam)).select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()).build();
    }

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilter() {
        final FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthenticationFilter());
        registrationBean.addUrlPatterns("/rest/*");

        return registrationBean;
    }
}
