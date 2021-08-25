package com.joepalmeras.webcrawler.csvcrawler;

import org.apache.commons.csv.CSVRecord;

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
import com.joepalmeras.webcrawler.csvcrawler.model.TimeSeriesCovid19VaccineDosesAdminGlobalDTO;
import com.joepalmeras.webcrawler.csvcrawler.model.VaccineDataGlobalBaseTable;
import com.joepalmeras.webcrawler.csvcrawler.reader.TimeSeriesCovid19VaccineDosesAdminGlobalEventReader;
import com.joepalmeras.webcrawler.csvcrawler.reader.VaccineDataGlobalBaseEventReader;
import com.joepalmeras.webcrawler.csvcrawler.processor.TimeSeriesCovid19VaccineDosesAdminGlobalEventProcessor;
import com.joepalmeras.webcrawler.csvcrawler.processor.VaccineDataGlobalBaseEventProcessor;
import com.joepalmeras.webcrawler.csvcrawler.writer.TimeSeriesCovid19VaccineDosesAdminGlobalAggregator;
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

	// JobCompletionNotificationListener (Database loader)
	@Bean
	public JobExecutionListener listener() {
		return new JobCompletionNotificationListener();
	}

	// Configure job step
	@Bean
	public Job vaccineDataGlobalETLJob() {
		return jobBuilderFactory.get("Fetch CSV Covid-19 Data ETL Job")
		        .incrementer(new RunIdIncrementer()).listener(listener())
//				.flow(etlVaccineDataGlobalBaseStep())
				.flow(etlTimeSeriesCovid19VaccineDosesAdminGlobalStep())
				.end()
				.build();
	}
	
	@Bean
	public TimeSeriesCovid19VaccineDosesAdminGlobalEventReader timeSeriesCovid19VaccineDosesAdminGlobalEventReader() {
		return new TimeSeriesCovid19VaccineDosesAdminGlobalEventReader();
	}

	@Bean
	public TimeSeriesCovid19VaccineDosesAdminGlobalEventProcessor timeSeriesCovid19VaccineDosesAdminGlobalEventProcessor() {
		return new TimeSeriesCovid19VaccineDosesAdminGlobalEventProcessor();
	}
	
	@Bean
	public TimeSeriesCovid19VaccineDosesAdminGlobalAggregator timeSeriesCovid19VaccineDosesAdminGlobalAggregator() {
		return new TimeSeriesCovid19VaccineDosesAdminGlobalAggregator();
	}
	
	@Bean
	public Step etlVaccineDataGlobalBaseStep() {
		return stepBuilderFactory.get("Extract -> Transform -> Aggregate -> Load: 1")
		        .<VaccineDataGlobalBaseTableDTO, VaccineDataGlobalBaseTable> chunk(1)
				.reader(vaccineDataGlobalBaseEventReader())
				.processor(vaccineDataGlobalBaseEventProcessor())
				.writer(vaccineDataGlobalBaseAggregator())
				.build();
	}

	@Bean
	public Step etlTimeSeriesCovid19VaccineDosesAdminGlobalStep() {
		return stepBuilderFactory.get("Extract -> Transform -> Aggregate -> Load: 2")
		        .<CSVRecord, TimeSeriesCovid19VaccineDosesAdminGlobalDTO> chunk(1)
				.reader(timeSeriesCovid19VaccineDosesAdminGlobalEventReader())
				.processor(timeSeriesCovid19VaccineDosesAdminGlobalEventProcessor())
				.writer(timeSeriesCovid19VaccineDosesAdminGlobalAggregator())
				.build();
	}
}
