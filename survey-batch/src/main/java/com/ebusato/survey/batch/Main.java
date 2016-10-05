package com.ebusato.survey.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.ebusato.survey.batch.config.BatchConfig;

public class Main {
	
	final static Logger LOGGER = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		LOGGER.debug("Starting application...");
		AbstractApplicationContext appContext = null;	        
		try {	          
			appContext = new AnnotationConfigApplicationContext(BatchConfig.class);
			LOGGER.debug("Context loaded, registering shutdown hook...");
			appContext.registerShutdownHook();			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			if (appContext != null) {
				LOGGER.debug("Closing application context...");
				((AnnotationConfigApplicationContext) appContext).close();
			}
		}	
		
	}
}
