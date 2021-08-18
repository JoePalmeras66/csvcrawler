package com.joepalmeras.webcrawler.csvcrawler.model;

import com.joepalmeras.webcrawler.csvcrawler.utils.VaccineDataGlobalBaseTableEnums;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VaccineDataGlobalBaseTableDTO {
	private String uid;
	private String province_state;
	private String country_region;
	private String date;
	private String doses_admin;
	private String people_partially_vaccinated;
	private String people_fully_vaccinated;
	private String report_date_string;
}