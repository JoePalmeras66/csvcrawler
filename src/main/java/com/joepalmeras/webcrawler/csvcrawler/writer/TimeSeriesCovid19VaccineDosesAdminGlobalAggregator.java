package com.joepalmeras.webcrawler.csvcrawler.writer;

import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;

import lombok.extern.slf4j.Slf4j;

import com.joepalmeras.webcrawler.csvcrawler.model.TimeSeriesCovid19VaccineDosesAdminGlobalDTO;

@Slf4j
public class TimeSeriesCovid19VaccineDosesAdminGlobalAggregator implements ItemWriter<TimeSeriesCovid19VaccineDosesAdminGlobalDTO>, StepExecutionListener {

	@Override
	public void beforeStep(StepExecution stepExecution) {
		
	}
	
	@Override
	public void write(List<? extends TimeSeriesCovid19VaccineDosesAdminGlobalDTO> items) throws Exception {
		log.info("Load: " + items.toString());
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		return ExitStatus.COMPLETED;
	}

}
