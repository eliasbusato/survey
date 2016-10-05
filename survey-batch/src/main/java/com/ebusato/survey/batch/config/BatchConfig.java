package com.ebusato.survey.batch.config;

import java.util.Properties;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;

import com.ebusato.survey.batch.listener.EmailNotification;
import com.ebusato.survey.batch.processor.MyItemProcessor;
import com.ebusato.survey.persistence.config.PersistenceConfig;
import com.ebusato.survey.persistence.entity.Lead;
import com.ebusato.survey.persistence.repository.LeadRepository;

import freemarker.template.TemplateExceptionHandler;

@Configuration
@EnableScheduling
@EnableBatchProcessing
@EnableAspectJAutoProxy(proxyTargetClass=true)
@ComponentScan(basePackageClasses = com.ebusato.survey.batch._PackageMarker.class)
@Import(PersistenceConfig.class)
@PropertySource("classpath:batch.properties")
public class BatchConfig extends DefaultBatchConfigurer {

	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private StepBuilderFactory steps;
	
	@Bean
	public ResourcelessTransactionManager resourcelessTransactionManager() {
		return new ResourcelessTransactionManager();
	}

	@Bean
	public JobRepository jobRepository(ResourcelessTransactionManager transactionManager) throws Exception {
		MapJobRepositoryFactoryBean mapJobRepositoryFactoryBean = new MapJobRepositoryFactoryBean(transactionManager);
		mapJobRepositoryFactoryBean.setTransactionManager(transactionManager);
		return mapJobRepositoryFactoryBean.getObject();
	}

	@Bean
	public SimpleJobLauncher jobLauncher(JobRepository jobRepository) {
		SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
		simpleJobLauncher.setJobRepository(jobRepository);
		return simpleJobLauncher;
	}

	@Bean	
	public freemarker.template.Configuration freemarkerConfiguration() throws Exception {
		freemarker.template.Configuration freemarkerConfig = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23);
		freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/");		
        freemarkerConfig.setDefaultEncoding("UTF-8");
        freemarkerConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        freemarkerConfig.setLogTemplateExceptions(false);        
        return freemarkerConfig;
	}

	@Bean
	public JavaMailSender javaMailService(
		@Value("${mail.host}") String mailHost,
			@Value("${mail.port}") Integer mailPort,
				@Value("${mail.login}") String mailLogin,
					@Value("${mail.password}")String mailPassword) {
		
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost(mailHost);
		javaMailSender.setPort(mailPort);
		javaMailSender.setUsername(mailLogin);
		javaMailSender.setPassword(mailPassword);		
		
		Properties javaMailProperties = new Properties();		
		javaMailProperties.setProperty("mail.smtp.auth", "true");		
		javaMailProperties.setProperty("mail.smtp.starttls.enable", "true");
		javaMailProperties.setProperty("mail.smtp.quitwait", "false");
		javaMailProperties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		javaMailProperties.setProperty("mail.smtp.socketFactory.fallback", "false");
		javaMailProperties.setProperty("mail.debug", "true");		
		javaMailSender.setJavaMailProperties(javaMailProperties);
		
		return javaMailSender;
	}

	@Bean
	public Job job(Step leadStep) {
		return jobs.get("leadJob").start(leadStep).build();
	}

	@Bean
	public Step leadStep(LeadRepository repository, MyItemProcessor processor, @Qualifier("transactionManager") PlatformTransactionManager txManager, EmailNotification emailNotification) {

		RepositoryItemReader<Lead> reader = new RepositoryItemReader<>();
		reader.setRepository(repository);
		reader.setMethodName("findByEngagedFalse");

		RepositoryItemWriter<Lead> writer = new RepositoryItemWriter<>();
		writer.setRepository(repository);
		writer.setMethodName("save");

		return steps.get("leadStep").<Lead, Lead>chunk(10)
					.reader(reader)
						.processor(processor)
							.writer(writer)
								.listener(emailNotification)
									.transactionManager(txManager)
										.build();
	}
	
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}