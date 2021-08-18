package com.joepalmeras.webcrawler.csvcrawler.utils;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Reader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;

import com.joepalmeras.webcrawler.csvcrawler.model.VaccineDataGlobalBaseTableDTO;
import com.joepalmeras.webcrawler.csvcrawler.utils.VaccineDataGlobalBaseTableEnums;

@Slf4j
@Service
public class VaccineDataGlobalBaseUtil {

	public List<VaccineDataGlobalBaseTableDTO> extractTo(String[] HEADERS, InputStream inputStream) throws IOException {
		
		CSVFormat FORMAT = CSVFormat.DEFAULT.withHeader(HEADERS)
											.withFirstRecordAsHeader();
		
		List<VaccineDataGlobalBaseTableDTO> listVaccineDataGlobalDTO = new ArrayList<>();
		
		try(Reader reader = new InputStreamReader(inputStream)) {
			
			Iterable<CSVRecord> records = FORMAT.parse(reader);
			
			for(CSVRecord record : records) {
			
				VaccineDataGlobalBaseTableDTO vaccineDataGlobalBaseTableDTO = VaccineDataGlobalBaseTableDTO.builder()
															   .province_state(record.get("Province_State"))
															   .country_region(record.get("Country_Region"))
															   .date(record.get("Date"))
															   .doses_admin(record.get("Doses_admin"))
															   .people_partially_vaccinated(record.get("People_partially_vaccinated"))
															   .people_fully_vaccinated(record.get("People_fully_vaccinated"))
															   .report_date_string(record.get("Report_Date_String"))
															   .uid(record.get("UID"))
															   .build();
			
				listVaccineDataGlobalDTO.add(vaccineDataGlobalBaseTableDTO);
			}
			
			return listVaccineDataGlobalDTO;
		}
	}
}