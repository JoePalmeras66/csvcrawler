package com.joepalmeras.webcrawler.csvcrawler.model;

import com.joepalmeras.webcrawler.csvcrawler.utils.VaccineDataGlobalBaseTableEnums;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Table(name="Vaccine_Data_Global_Base_Table")
public class VaccineDataGlobalBaseTable implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long uid;
	private String province_state;
	private String country_region;
	private Date date;
	private Long doses_admin;
	private Long people_partially_vaccinated;
	private Long people_fully_vaccinated;
	private Date report_date_string;
	private Integer table_type;
}