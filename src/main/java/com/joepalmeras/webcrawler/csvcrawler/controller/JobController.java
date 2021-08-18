package com.joepalmeras.webcrawler.csvcrawler.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;
import java.net.URL;

import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;

@Slf4j
@RestController
@RequestMapping("/api/csv")
public class JobController {
	
	@Autowired
    JobLauncher jobLauncher;
	
	@Autowired
    Job job;

    @GetMapping("/runJob")
	public HttpEntity<String> runJob() throws JobExecutionAlreadyRunningException, JobRestartException, 
	                                          JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		
		JobParameters jobParameters = new JobParametersBuilder()
                       .addLong("time",System.currentTimeMillis()).toJobParameters(); 
					   
		jobLauncher.run(job, jobParameters);
				
		return ResponseEntity.ok()
			.body("Start ETL-Job: Read & Store CSV-Data");
	}
	
}