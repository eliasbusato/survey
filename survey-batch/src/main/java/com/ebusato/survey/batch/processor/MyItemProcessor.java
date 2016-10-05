package com.ebusato.survey.batch.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.ebusato.survey.batch.Main;
import com.ebusato.survey.persistence.entity.Lead;

@Component
public class MyItemProcessor implements ItemProcessor<Lead, Lead>  {

	final static Logger LOGGER = LoggerFactory.getLogger(Main.class);
	
	@Override
	public Lead process(Lead item) throws Exception {
		item.setEngaged(true);
		LOGGER.info("lead with email [{}] was set to engaged!", item.getEmail());
		return item;
	}
}
