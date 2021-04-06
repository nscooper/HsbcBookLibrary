package com.nscooper.hsbc.library.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig {
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis( RequestHandlerSelectors.basePackage("com.nscooper.hsbc.library.controllers") )
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}

	public final ApiInfo apiInfo() {
		return new ApiInfo("LIBRARY REST API",
				"Welcome to HSBC Library",
				"API ToS",
				"Terms of service",
				new Contact("Nick Cooper",
						"http://www.cooperappliedsolutions.com/",
						"nick@cooperappliedsolutions.com"),
				"License of API",
				"API license URL", Collections.emptyList());
	}
}