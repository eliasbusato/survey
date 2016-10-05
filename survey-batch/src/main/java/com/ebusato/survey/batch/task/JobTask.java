package com.ebusato.survey.batch.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobTask {
	
	static final Logger LOGGER = LoggerFactory.getLogger(JobTask.class);
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired	
	private Job job;
		
	@Scheduled(fixedRateString= "${job.rate}")
	public void runJob() {
		try {
			long startTime = System.currentTimeMillis();
			LOGGER.info("Launching Lead Job.");			
			jobLauncher.run(job, new JobParametersBuilder().addDate("startDate", new Date()).toJobParameters());
			LOGGER.info("Job finished! took [{}] ms!", System.currentTimeMillis() - startTime);
		} catch (Exception e) {
			LOGGER.error("Error running Lead Job", e);			
		}		
	}
}
