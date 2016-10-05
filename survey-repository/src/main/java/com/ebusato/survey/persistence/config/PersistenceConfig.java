package com.ebusato.survey.persistence.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ebusato.survey.persistence.entity.Lead;

@Configuration
@EnableJpaRepositories(basePackageClasses=com.ebusato.survey.persistence.repository._PackageMarker.class)
@EnableTransactionManagement
@PropertySource("classpath:datasource.properties")
public class PersistenceConfig {
	
	@Bean
    public DataSource dataSource(
    	@Value("${datasource.driver.classname}") String driverClassName,
    		@Value("${datasource.url}") String datasourceUrl,
    			@Value("${datasource.username}") String datasourceUsername,
    				@Value("${datasource.password}") String datasourcePassword) {
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(datasourceUrl);
		dataSource.setUsername(datasourceUsername);
		dataSource.setPassword(datasourcePassword);
	    return dataSource;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter(@Value("${jpa.showsql}") boolean showSql) {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(showSql);
        hibernateJpaVendorAdapter.setGenerateDdl(false);
        hibernateJpaVendorAdapter.setDatabase(Database.POSTGRESQL);
        return hibernateJpaVendorAdapter;
    }
    	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
	    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
	    em.setDataSource(dataSource);
	    em.setJpaVendorAdapter(jpaVendorAdapter);
	    Properties jpaProperties = new Properties();
	    jpaProperties.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");
		em.setJpaProperties(jpaProperties );
	    em.setPackagesToScan(Lead.class.getPackage().getName());
	    return em;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory em) {
		return new JpaTransactionManager(em);
	}
}