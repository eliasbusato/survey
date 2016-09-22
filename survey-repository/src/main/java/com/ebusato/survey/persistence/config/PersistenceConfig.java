package com.ebusato.survey.persistence.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.ebusato.survey.persistence.entity.Lead;

@Configuration
@EnableJpaRepositories(basePackageClasses=com.ebusato.survey.persistence.repository._PackageMarker.class)
@PropertySource("classpath:datasource.properties")
public class PersistenceConfig {
	
	@Value("${datasource.driver.classname}")
	private String driverClassName;
	
	@Value("${datasource.url}")
	private String datasourceUrl;

	@Value("${datasource.username}")
	private String datasourceUsername;
	
	@Value("${datasource.password}")
	private String datasourcePassword;
	
	@Bean
    public DataSource dataSource() {
		DriverManagerDataSource driver = new DriverManagerDataSource();
	    driver.setDriverClassName(this.driverClassName);
	    driver.setUrl(this.datasourceUrl);
	    driver.setUsername(this.datasourceUsername);
	    driver.setPassword(this.datasourcePassword);
	    return driver;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(true);
        hibernateJpaVendorAdapter.setGenerateDdl(false);
        hibernateJpaVendorAdapter.setDatabase(Database.POSTGRESQL);
        return hibernateJpaVendorAdapter;
    }
	
	@Bean
	public EntityManagerFactory entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
	    LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
	    lef.setDataSource(dataSource);
	    lef.setJpaVendorAdapter(jpaVendorAdapter);
	    lef.setPackagesToScan(Lead.class.getPackage().getName());
	    return lef.getObject();
	}
}
