package com.joepalmeras.webcrawler.csvcrawler.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@Table(name="time_series_covid19_vaccine_data")
public class TimeSeriesCovid19VaccineData {
	@Id
    @GeneratedValue
    private Long id;
	
	private LocalDate localDate;
	private Long amount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private TimeSeriesCovid19VaccineDosesAdminGlobal timeSeriesCovid19VaccineDosesAdminGlobal;
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeSeriesCovid19VaccineData)) return false;
        return id != null && id.equals(((TimeSeriesCovid19VaccineData)o).getId());
    }
	
	@Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
