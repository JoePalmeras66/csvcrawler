package com.joepalmeras.webcrawler.csvcrawler.processor;

import org.springframework.batch.item.ItemProcessor;

import lombok.extern.slf4j.Slf4j;
import java.text.SimpleDateFormat;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

import com.joepalmeras.webcrawler.csvcrawler.model.VaccineDataGlobalBaseTable;
import com.joepalmeras.webcrawler.csvcrawler.model.VaccineDataGlobalBaseTableDTO;
import com.joepalmeras.webcrawler.csvcrawler.utils.VaccineDataGlobalBaseTableEnums;

@Slf4j
public class VaccineDataGlobalBaseEventProcessor implements ItemProcessor<VaccineDataGlobalBaseTableDTO, VaccineDataGlobalBaseTable>, StepExecutionListener {
	
	@Override
    public void beforeStep(StepExecution stepExecution) {
	
	}
	
	@Override
	public VaccineDataGlobalBaseTable process(final VaccineDataGlobalBaseTableDTO vaccineDataGlobalBaseTableDTO) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf_slash = new SimpleDateFormat("yyyy/MM/dd");
		
		String strProvince_state = (vaccineDataGlobalBaseTableDTO.getProvince_state() != null) ? vaccineDataGlobalBaseTableDTO.getProvince_state() : new String();
		String strCountry_region = (vaccineDataGlobalBaseTableDTO.getCountry_region() != null) ? vaccineDataGlobalBaseTableDTO.getCountry_region() : new String();
		String strDate = (vaccineDataGlobalBaseTableDTO.getDate() != null) ? vaccineDataGlobalBaseTableDTO.getDate() : new String();
		String strDoses_admin = (vaccineDataGlobalBaseTableDTO.getDoses_admin() != null) ? vaccineDataGlobalBaseTableDTO.getDoses_admin() : new String();
		String strPeople_partially_vaccinated = (vaccineDataGlobalBaseTableDTO.getPeople_partially_vaccinated() != null) ? vaccineDataGlobalBaseTableDTO.getPeople_partially_vaccinated() : new String();
		String strPeople_fully_vaccinated =  (vaccineDataGlobalBaseTableDTO.getPeople_fully_vaccinated() != null) ? vaccineDataGlobalBaseTableDTO.getPeople_fully_vaccinated() : new String();
		String strReport_date_string = (vaccineDataGlobalBaseTableDTO.getReport_date_string() != null) ? vaccineDataGlobalBaseTableDTO.getReport_date_string() : new String();
		String strUid = (vaccineDataGlobalBaseTableDTO.getUid() != null) ? vaccineDataGlobalBaseTableDTO.getUid() : new String();
		
		VaccineDataGlobalBaseTable vaccineDataGlobalBaseTable = VaccineDataGlobalBaseTable.builder()
		                                                       .table_type(VaccineDataGlobalBaseTableEnums.VACCINE.ordinal())
		                                                       .build();

        if(!strProvince_state.isEmpty())
			vaccineDataGlobalBaseTable.setProvince_state(strProvince_state);
		if(!strCountry_region.isEmpty())
			vaccineDataGlobalBaseTable.setCountry_region(strCountry_region);
		if(!strDate.isEmpty())
			vaccineDataGlobalBaseTable.setDate(sdf.parse(strDate));
		if(!strDoses_admin.isEmpty())
			vaccineDataGlobalBaseTable.setDoses_admin(Long.parseLong(strDoses_admin));
		if(!strPeople_partially_vaccinated.isEmpty())
			vaccineDataGlobalBaseTable.setPeople_partially_vaccinated(Long.parseLong(strPeople_partially_vaccinated));
		if(!strPeople_fully_vaccinated.isEmpty())
			vaccineDataGlobalBaseTable.setPeople_fully_vaccinated(Long.parseLong(strPeople_fully_vaccinated));
		if(!strReport_date_string.isEmpty())
			vaccineDataGlobalBaseTable.setReport_date_string(sdf_slash.parse(strReport_date_string));
		if(!strUid.isEmpty())
			vaccineDataGlobalBaseTable.setUid(Long.parseLong(strUid));
		
		log.info("Transform: " + vaccineDataGlobalBaseTable.toString());
		
		return vaccineDataGlobalBaseTable;
	}
	
	@Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }
}
