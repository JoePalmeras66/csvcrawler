package com.joepalmeras.webcrawler.csvcrawler.model;

import java.util.List;

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
public class TimeSeriesCovid19VaccineDosesAdminGlobalDTO {
	private String uid;
	private String iso2;
	private String iso3;
	private String code3;
	private String fips;
	private String admin2;
	private String province_state;
	private String country_region;
	private String lat;
	private String long_;
	private String combined_key;
	private String population;
	private List<TimeSeriesCovid19VaccineDataDTO> timeSeries;
}