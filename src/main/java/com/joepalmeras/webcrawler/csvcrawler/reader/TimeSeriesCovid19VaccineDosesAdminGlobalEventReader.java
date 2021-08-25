package com.joepalmeras.webcrawler.csvcrawler.reader;

import java.net.URL;

import java.io.InputStream;
import java.io.IOException;

import java.util.List;
import org.springframework.batch.item.ItemReader;

import org.springframework.web.util.UriComponentsBuilder;

import org.springframework.beans.factory.annotation.Autowired;

import org.apache.commons.csv.CSVRecord;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

import com.joepalmeras.webcrawler.csvcrawler.utils.TimeSeriesCovid19VaccineDosesAdminGlobalUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeSeriesCovid19VaccineDosesAdminGlobalEventReader implements ItemReader<CSVRecord>, StepExecutionListener {
	
	protected List<CSVRecord> listCSVRecords;
	protected int nextCSVRecordIndex;
	
	protected InputStream inputStream;
	
	@Autowired
	protected TimeSeriesCovid19VaccineDosesAdminGlobalUtil util;

	@Override
    public void beforeStep(StepExecution stepExecution) {

		try {
			
			URL urlCsvFile = UriComponentsBuilder.newInstance()
												.scheme("https")
												.host("raw.githubusercontent.com")
												.path("JoePalmeras66/TestData/main/time_series_covid19_vaccine_doses_admin_global.csv")
												.build()
												.toUri()
												.toURL();
												
		    this.inputStream = urlCsvFile.openStream();
			
		    this.listCSVRecords = util.extractTo(this.inputStream);
			
		} catch(IOException ex) {
			
		}
		 
		nextCSVRecordIndex = 0;
	}
	
	@Override
    public CSVRecord read() {
        CSVRecord nextCSVRecord = null;
 
        if (nextCSVRecordIndex < listCSVRecords.size()) {
        	nextCSVRecord = listCSVRecords.get(nextCSVRecordIndex);
			
			log.info("Extract: " + nextCSVRecord.toString());
            nextCSVRecordIndex++;
        }
        else {
            nextCSVRecordIndex = 0;
        }
 
        return nextCSVRecord;
    }

	@Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        try {
			this.inputStream.close();
		} catch(IOException ex){
			
		}
        return ExitStatus.COMPLETED;
    }

}
