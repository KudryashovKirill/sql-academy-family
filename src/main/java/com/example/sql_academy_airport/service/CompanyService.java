package com.example.sql_academy_airport.service;

import com.example.sql_academy_airport.dto.CompanyDto;

import java.util.List;
import java.util.Map;

public interface CompanyService {
    CompanyDto create(CompanyDto companyDto);

    CompanyDto getById(Long id);

    CompanyDto update(CompanyDto companyDto, Long id);

    Map<String, Boolean> delete(Long id);

    List<String> getAllNames();

    List<String> getCompaniesByPlaneName(String planeName);

    List<String> getAllTownFrom(String townFrom);
}
