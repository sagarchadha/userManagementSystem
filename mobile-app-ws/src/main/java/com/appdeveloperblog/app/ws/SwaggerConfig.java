package com.appdeveloperblog.app.ws;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	Contact contact = new Contact("Sagar Chadha", null, "sagarchadha@gmail.com");

	ApiInfo apiInfo = new ApiInfo("User Management RESTful Web Service Documention",
			"This page documents all of the REST endpoints for a user management service.", "1.0", null, contact,
			"Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList<>());

	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo).select()
				.apis(RequestHandlerSelectors.basePackage("com.appdeveloperblog.app.ws")).paths(PathSelectors.any())
				.build();
	}
}
