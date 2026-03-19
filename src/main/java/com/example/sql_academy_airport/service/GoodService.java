package com.example.sql_academy_airport.service;

import com.example.sql_academy_airport.dto.input.GoodInputDto;
import com.example.sql_academy_airport.dto.output.GoodOutputDto;

import java.util.Map;

public interface GoodService {
    GoodOutputDto create(GoodInputDto goodInputDto);

    GoodOutputDto getById(Long id);

    GoodOutputDto update(GoodInputDto goodInputDto, Long id);

    Map<String, Boolean> delete(Long id);

    Map<String, Integer> getMostExpensiveGood(String goodTypeName, Integer limit);
}
