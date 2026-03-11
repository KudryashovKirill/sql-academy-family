package com.example.sql_academy_airport.repository;

import com.example.sql_academy_airport.model.Company;

import java.util.List;
import java.util.Map;

public interface CompanyRepository {
    Company create(Company company);

    Company getById(Long id);

    Company update(Company company, Long id);

    Map<String, Boolean> delete(Long id);

    List<String> getAllNames();

    List<String> getCompaniesByPlaneName(String planeName);

    List<String> getAllTownFrom(String townFrom);
}
