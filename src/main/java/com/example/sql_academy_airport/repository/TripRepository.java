package com.example.sql_academy_airport.repository;

import com.example.sql_academy_airport.model.Trip;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TripRepository {
    Trip create(Trip trip);

    Trip getById(Long id);

    Trip update(Trip trip, Long id);

    Map<String, Boolean> delete(Long id);

    List<Trip> getAllFromTownFrom(String townFrom);

    Integer getAllPlanesByName(String name);

    List<String> getAllPlanesByTownTo(String townTo);

    Map<String, Duration> getAllFromTownFromMap(String townFrom);

    List<Trip> getAllBetweenTime(LocalDate timeStart, LocalDate timeEnd);
}
