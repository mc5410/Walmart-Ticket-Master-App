package com.walmart.ticket_master;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.walmart" })
public class TicketMasterApp extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(TicketMasterApp.class, args);
	}
	
	@Override
	    protected final SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
	
	        return application.sources(TicketMasterApp.class,JPAConfig.class,AppConfig.class);
	
	    }

}