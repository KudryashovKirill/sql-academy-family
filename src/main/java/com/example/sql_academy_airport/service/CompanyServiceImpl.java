package com.example.sql_academy_airport.service;

import com.example.sql_academy_airport.dto.CompanyDto;
import com.example.sql_academy_airport.model.Company;
import com.example.sql_academy_airport.repository.CompanyRepository;
import com.example.sql_academy_airport.util.CompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CompanyServiceImpl implements CompanyService {
    private CompanyRepository companyRepository;
    private CompanyMapper mapper;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, CompanyMapper mapper) {
        this.companyRepository = companyRepository;
        this.mapper = mapper;
    }

    @Override
    public CompanyDto create(CompanyDto companyDto) {
        Company company = mapper.toEntity(companyDto);
        return mapper.toDto(companyRepository.create(company));
    }

    @Override
    public CompanyDto getById(Long id) {
        return mapper.toDto(companyRepository.getById(id));
    }

    @Override
    public CompanyDto update(CompanyDto companyDto, Long id) {
        Company company = mapper.toEntity(companyDto);
        mapper.updateEntityFromDto(companyDto, company);
        return mapper.toDto(companyRepository.update(company, id));
    }
    @Override
    public List<String> getAllNames() {
        return companyRepository.getAllNames();
    }
    @Override
    public List<String> getCompaniesByPlaneName(String planeName) {
        return companyRepository.getCompaniesByPlaneName(planeName);
    }
    @Override
    public List<String> getAllTownFrom(String townFrom) {
        return companyRepository.getAllTownFrom(townFrom);
    }

    @Override
    public Map<String, Boolean> delete(Long id) {
        return companyRepository.delete(id);
    }
}
