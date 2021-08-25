package com.joepalmeras.webcrawler.csvcrawler.model;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Entity
@Data
@Builder
@EqualsAndHashCode(callSuper=false)
@Table(name="time_series_covid19_vaccine_doses_admin_global")
public class TimeSeriesCovid19VaccineDosesAdminGlobal implements Serializable {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
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
	
	@OneToMany(
        mappedBy = "time_series_covid19_vaccine_doses_admin_global",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
	private List<TimeSeriesCovid19VaccineData> timeSeriesList = new ArrayList<>();
	
	public void addTimeSeries(TimeSeriesCovid19VaccineData timeSeries) {
		timeSeriesList.add(timeSeries);
		timeSeries.setTimeSeriesCovid19VaccineDosesAdminGlobal(this);
    }
 
    public void removeTimeSeries(TimeSeriesCovid19VaccineData timeSeries) {
    	timeSeriesList.remove(timeSeries);
    	timeSeries.setTimeSeriesCovid19VaccineDosesAdminGlobal(null);
    }
}