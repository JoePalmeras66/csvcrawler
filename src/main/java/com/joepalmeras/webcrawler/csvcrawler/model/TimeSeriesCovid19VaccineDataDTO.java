package com.joepalmeras.webcrawler.csvcrawler.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TimeSeriesCovid19VaccineDataDTO {
	private String localDate;
	private String amount;
}
