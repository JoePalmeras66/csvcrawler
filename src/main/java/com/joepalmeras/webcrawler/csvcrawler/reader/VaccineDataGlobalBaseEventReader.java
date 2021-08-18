package com.joepalmeras.webcrawler.csvcrawler.reader;

import java.net.URI;
import java.net.URL;

import java.io.InputStream;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;

import org.springframework.batch.item.ItemReader;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

import com.joepalmeras.webcrawler.csvcrawler.model.VaccineDataGlobalBaseTableDTO;
import com.joepalmeras.webcrawler.csvcrawler.utils.VaccineDataGlobalBaseUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VaccineDataGlobalBaseEventReader implements ItemReader<VaccineDataGlobalBaseTableDTO>, StepExecutionListener {
	
	private static final String[] HEADERS = {"Province_State", "Country_Region", "Date", "Doses_admin", "People_partially_vaccinated", 
	                                         "People_fully_vaccinated", "Report_Date_String", "UID"};
	
	protected List<VaccineDataGlobalBaseTableDTO> listVaccineDataGlobalBaseTableDTO;
	protected int nextVaccineDataGlobalIndex;
	
	protected InputStream inputStream;
	
	@Autowired
	protected VaccineDataGlobalBaseUtil util;

	@Override
    public void beforeStep(StepExecution stepExecution) {

		try {
			
			URL urlCsvFile = UriComponentsBuilder.newInstance()
												.scheme("https")
												.host("raw.githubusercontent.com")
												.path("govex/COVID-19/master/data_tables/vaccine_data/global_data/vaccine_data_global.csv")
												.build()
												.toUri()
												.toURL();
												
		    this.inputStream = urlCsvFile.openStream();
			
		    this.listVaccineDataGlobalBaseTableDTO = util.extractTo(this.HEADERS, this.inputStream);
			
		} catch(IOException ex) {
			
		}
		 
		nextVaccineDataGlobalIndex = 0;
	}
	
	@Override
    public VaccineDataGlobalBaseTableDTO read() {
        VaccineDataGlobalBaseTableDTO nextVaccineDataGlobalBaseTableDTO = null;
 
        if (nextVaccineDataGlobalIndex < listVaccineDataGlobalBaseTableDTO.size()) {
            nextVaccineDataGlobalBaseTableDTO = listVaccineDataGlobalBaseTableDTO.get(nextVaccineDataGlobalIndex);
			
			log.info("Extract: " + nextVaccineDataGlobalBaseTableDTO.toString());
            nextVaccineDataGlobalIndex++;
        }
        else {
            nextVaccineDataGlobalIndex = 0;
        }
 
        return nextVaccineDataGlobalBaseTableDTO;
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
