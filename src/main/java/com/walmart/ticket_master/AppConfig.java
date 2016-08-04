package com.walmart.ticket_master;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
@ComponentScan
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter{
	
	public void addResourceHandler( ResourceHandlerRegistry registry){
		
//		registry.addResourceHandler("swagger-ui.html").addResourceLocations(
//				"classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**")
		.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/*").allowedOrigins("*")
				.allowedMethods("GET", "POST", "PUT", "DELETE")
				.allowCredentials(false).maxAge(3600);
	}

}
