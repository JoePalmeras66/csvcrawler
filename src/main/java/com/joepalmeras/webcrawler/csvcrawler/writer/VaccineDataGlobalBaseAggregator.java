package com.joepalmeras.webcrawler.csvcrawler.writer;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

import com.joepalmeras.webcrawler.csvcrawler.model.VaccineDataGlobalBaseTable;
import com.joepalmeras.webcrawler.csvcrawler.repository.VaccineDataGlobalBaseRepository;

@Slf4j
public class VaccineDataGlobalBaseAggregator implements ItemWriter<VaccineDataGlobalBaseTable>, StepExecutionListener {

	@Autowired
	VaccineDataGlobalBaseRepository repository;
	
	@Override
    public void beforeStep(StepExecution stepExecution) {
	
	}
	
	@Override
	public void write(List<? extends VaccineDataGlobalBaseTable> listVaccineDataGlobalBaseTable) throws Exception {
		// Save Data in Database
		repository.saveAll(listVaccineDataGlobalBaseTable);
		log.info("Load: " + listVaccineDataGlobalBaseTable.toString());
	}
	
	@Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }

}
