package com.joepalmeras.webcrawler.csvcrawler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.joepalmeras.webcrawler.csvcrawler.model.VaccineDataGlobalBaseTableDTO;
import com.joepalmeras.webcrawler.csvcrawler.model.VaccineDataGlobalBaseTable;
import com.joepalmeras.webcrawler.csvcrawler.reader.VaccineDataGlobalBaseEventReader;
import com.joepalmeras.webcrawler.csvcrawler.processor.VaccineDataGlobalBaseEventProcessor;
import com.joepalmeras.webcrawler.csvcrawler.writer.VaccineDataGlobalBaseAggregator;
import com.joepalmeras.webcrawler.csvcrawler.listener.JobCompletionNotificationListener;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	// VaccineDataGlobalBaseEventReader (Reader)
	@Bean
	public VaccineDataGlobalBaseEventReader vaccineDataGlobalBaseEventReader() {
		return new VaccineDataGlobalBaseEventReader();
	}

	// VaccineDataGlobalBaseEventProcessor (Processor)
	@Bean
	public VaccineDataGlobalBaseEventProcessor vaccineDataGlobalBaseEventProcessor() {
		return new VaccineDataGlobalBaseEventProcessor();
	}

	// VaccineDataGlobalBaseAggregator (Writer)
	@Bean
	public VaccineDataGlobalBaseAggregator vaccineDataGlobalBaseAggregator() {
		return new VaccineDataGlobalBaseAggregator();
	}

	// JobCompletionNotificationListener (File loader)
	@Bean
	public JobExecutionListener listener() {
		return new JobCompletionNotificationListener();
	}

	// Configure job step
	@Bean
	public Job vaccineDataGlobalETLJob() {
		return jobBuilderFactory.get("Fetch CSV Covid-19 Data ETL Job")
		        .incrementer(new RunIdIncrementer()).listener(listener())
				.flow(etlVaccineDataGlobalBaseStep())
				.end()
				.build();
	}

	@Bean
	public Step etlVaccineDataGlobalBaseStep() {
		return stepBuilderFactory.get("Extract -> Transform -> Aggregate -> Load")
		        .<VaccineDataGlobalBaseTableDTO, VaccineDataGlobalBaseTable> chunk(1)
				.reader(vaccineDataGlobalBaseEventReader())
				.processor(vaccineDataGlobalBaseEventProcessor())
				.writer(vaccineDataGlobalBaseAggregator())
				.build();
	}

}
