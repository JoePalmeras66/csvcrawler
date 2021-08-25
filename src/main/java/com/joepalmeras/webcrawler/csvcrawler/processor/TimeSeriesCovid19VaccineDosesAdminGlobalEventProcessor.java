package com.joepalmeras.webcrawler.csvcrawler.processor;

import java.util.Map;

import org.apache.commons.csv.CSVRecord;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;

import com.joepalmeras.webcrawler.csvcrawler.model.TimeSeriesCovid19VaccineDataDTO;
import com.joepalmeras.webcrawler.csvcrawler.model.TimeSeriesCovid19VaccineDosesAdminGlobalDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeSeriesCovid19VaccineDosesAdminGlobalEventProcessor implements ItemProcessor<CSVRecord, TimeSeriesCovid19VaccineDosesAdminGlobalDTO>, StepExecutionListener{

	@Override
	public void beforeStep(StepExecution stepExecution) {
		
	}

	@Override
	public TimeSeriesCovid19VaccineDosesAdminGlobalDTO process(CSVRecord record) throws Exception {
		
		TimeSeriesCovid19VaccineDosesAdminGlobalDTO pojo = TimeSeriesCovid19VaccineDosesAdminGlobalDTO.builder()
				.uid(record.get("UID"))
				.iso2(record.get("ido2"))
				.iso3(record.get("ido3"))
				.code3(record.get("code3"))
				.fips(record.get("FIPS"))
				.admin2(record.get("Admin2"))
				.province_state(record.get("Province_State"))
				.country_region(record.get("Country_Region"))
				.lat(record.get("Lat"))
				.long_(record.get("Long_"))
				.combined_key(record.get("Combined_Key"))
				.population(record.get("Population"))
				.build();
		
		Map<String,String> mapData = record.toMap();
		
		int sizeMap = mapData.size();
		
//		Map<String,String> mapDateAmount = mapData.subMap(12, sizeMap-1);
		
		int rowNum = 0;
		for(Map.Entry<String, String> entry : mapData.entrySet()) {
			if(rowNum > 11) {
				TimeSeriesCovid19VaccineDataDTO data = TimeSeriesCovid19VaccineDataDTO.builder()
						.localDate(entry.getKey())
						.amount(entry.getValue())
						.build();
				
				pojo.getTimeSeries().add(data);
			}
			
			rowNum++;
		}
		
		log.info("Transform: " + pojo.toString());
		
		return pojo;
	}
	
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		return ExitStatus.COMPLETED;
	}

}
