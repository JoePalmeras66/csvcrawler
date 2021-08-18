package com.joepalmeras.webcrawler.csvcrawler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joepalmeras.webcrawler.csvcrawler.model.VaccineDataGlobalBaseTable;

@Repository
public interface VaccineDataGlobalBaseRepository extends JpaRepository<VaccineDataGlobalBaseTable, Long> {
}