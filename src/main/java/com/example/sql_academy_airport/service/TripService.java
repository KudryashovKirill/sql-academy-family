package com.example.sql_academy_airport.service;

import com.example.sql_academy_airport.dto.TripDto;
import com.example.sql_academy_airport.model.Trip;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface TripService {
    TripDto create(TripDto tripDto);

    TripDto getById(Long id);

    TripDto update(TripDto tripDto, Long id);

    Map<String, Boolean> delete(Long id);

    List<TripDto> getAllFromTownFrom(String townFrom);

    Integer getAllPlanesByName(String name);

    List<String> getAllPlanesByTownTo(String townTo);

    Map<String, Duration> getAllFromTownFromMap(String townFrom);

    List<TripDto> getAllBetweenTime(LocalDate timeStart, LocalDate timeEnd);
}
