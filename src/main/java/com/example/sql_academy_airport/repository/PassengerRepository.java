package com.example.sql_academy_airport.repository;

import com.example.sql_academy_airport.model.Passenger;

import java.util.List;
import java.util.Map;

public interface PassengerRepository {
    Passenger create(Passenger passenger);

    Passenger getById(Long id);

    Passenger update(Passenger passenger, Long id);

    Map<String, Boolean> delete(Long id);

    List<String> getAllNames();

    List<String> getAllEndWith(String namePostfix);

    List<String> getAllLongestNames();

    Map<Long, Integer> getCountPassengerByTrip();
}
