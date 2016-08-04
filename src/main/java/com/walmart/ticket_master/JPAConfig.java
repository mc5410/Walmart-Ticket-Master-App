package com.walmart.ticket_master;


import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySources({
		@PropertySource(value="classpath:app.properties"),
		@PropertySource(value="classpath:import.sql")})
public class JPAConfig {
	
	@Autowired
	private Environment env;
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
		
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(getDataSource());
		emf.setPackagesToScan( new String[]{ "com.walmart.ticket_master.entity"});
	    JpaVendorAdapter jpad = new HibernateJpaVendorAdapter();
	    emf.setJpaVendorAdapter(jpad);
	    emf.setJpaProperties(jpaProperties());
	    
	    return emf;		
		
	}

	
//	   @Bean(name = "dataSource")
//	    public DataSource getDataSource() {
//	        return new EmbeddedDatabaseBuilder()
//	                .setType(EmbeddedDatabaseType.HSQL)
//	                .setName("userdb")
//	                .build();
//	    }
	
	   @Bean
	    public DataSource getDataSource() {
	        DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
	        dataSource.setUrl("jdbc:hsqldb:mem:testdb");
	        dataSource.setUsername("root");
	        dataSource.setPassword("root");
	        return dataSource;
	    }
	   
		@Bean
		public PlatformTransactionManager transactionManager(
				EntityManagerFactory emf) {
			JpaTransactionManager txnManager = new JpaTransactionManager(emf);
			return txnManager;
		}
		
		private Properties jpaProperties(){
			
			 Properties properties = new Properties();
		        properties.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
		        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl"));
		        properties.put("hibernate.hbm2ddl.import_files","data-script.sql");
		        properties.put("connection.pool_size", "1");
		        properties.put("current_session_context_class", "thread");
		        properties.put("hibernate.show_sql", "true");
		        properties.put("hibernate.format_sql", "true");
		        properties.put("hibernate.hbm2ddl.import_files","import.sql");
		        return properties;
			
		}
	   
	   
	
	
}