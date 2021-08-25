package com.joepalmeras.webcrawler.csvcrawler.reader;

import java.net.URL;

import java.io.IOException;

import org.springframework.batch.core.StepExecution;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeSeriesCovid19VaccineGlobalEventReader extends VaccineDataGlobalBaseEventReader {
	
	private static final String[] HEADERS = {"Country_Region", "Date", "Doses_admin", "People_partially_vaccinated", 
	                                         "People_fully_vaccinated", "Report_Date_String", "UID", "Province_State"};

	public TimeSeriesCovid19VaccineGlobalEventReader() {
		log.info("ETL - Step: TimeSeriesCovid19VaccineGlobalEventReader");
	}
	
	@Override
    public void beforeStep(StepExecution stepExecution) {
		
		try {
			
			URL urlCsvFile = UriComponentsBuilder.newInstance()
												.scheme("https")
												.host("raw.githubusercontent.com")
												.path("JoePalmeras66/TestData/main/time_series_covid19_vaccine_global.csv")
												.build()
												.toUri()
												.toURL();
												
		    this.inputStream = urlCsvFile.openStream();
			
		    this.listVaccineDataGlobalBaseTableDTO = util.extractTo(this.HEADERS, this.inputStream);
			
		} catch(IOException ex) {
			
		}
		 
		nextVaccineDataGlobalIndex = 0;
	}

}
