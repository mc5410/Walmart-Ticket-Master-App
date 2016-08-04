package com.walmart.ticket_master;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo(){
		
		ApiInfo apiInfo = new ApiInfo(
										"Ticket-Master",
										"This app serves as a ticket reservation system", 
										"1.0.0", 
										"Ticket-Master Terms & Conditions",
										"mc5410@nyu.edu", 
										"walmart License", 
										"walmart License URL");
								return apiInfo;
			
		}
		
		
	}
