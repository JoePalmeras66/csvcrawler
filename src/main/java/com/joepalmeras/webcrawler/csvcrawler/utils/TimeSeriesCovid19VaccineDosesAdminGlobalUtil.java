package com.joepalmeras.webcrawler.csvcrawler.utils;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVParser;

import org.springframework.stereotype.Service;
import java.io.Reader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.List;

@Slf4j
@Service
public class TimeSeriesCovid19VaccineDosesAdminGlobalUtil {

	public List<CSVRecord> extractTo(InputStream inputStream) throws IOException {
		
		List<CSVRecord> listCSVRecord = null;
		CSVParser parser = null;
		
		try(Reader reader = new InputStreamReader(inputStream)) {
			
			parser = CSVParser.parse(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
			
			listCSVRecord = parser.getRecords();
			
		} finally {
          if(parser != null)
            parser.close();
        }
		
		return listCSVRecord;
	}
}