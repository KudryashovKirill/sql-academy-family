package com.example.sql_academy_airport.service;

import com.example.sql_academy_airport.dto.PassengerDto;
import com.example.sql_academy_airport.model.Passenger;
import com.example.sql_academy_airport.repository.PassengerRepository;
import com.example.sql_academy_airport.util.PassengerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PassengerServiceImpl implements PassengerService {
    private PassengerRepository passengerRepository;
    private PassengerMapper mapper;

    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository, PassengerMapper passengerMapper) {
        this.passengerRepository = passengerRepository;
        this.mapper = passengerMapper;
    }

    @Override
    public PassengerDto create(PassengerDto passengerDto) {
        Passenger passenger = mapper.toEntity(passengerDto);
        return mapper.toDto(passengerRepository.create(passenger));
    }

    @Override
    public PassengerDto getById(Long id) {
        return mapper.toDto(passengerRepository.getById(id));
    }

    @Override
    public PassengerDto update(PassengerDto passengerDto, Long id) {
        Passenger passenger = mapper.toEntity(passengerDto);
        mapper.updateEntityFromDto(passengerDto, passenger);
        return mapper.toDto(passengerRepository.update(passenger, id));
    }
    @Override
    public List<String> getAllNames() {
        return passengerRepository.getAllNames();
    }
    @Override
    public List<String> getAllEndWith(String namePostfix) {
        return passengerRepository.getAllEndWith(namePostfix);
    }
    @Override
    public List<String> getAllLongestNames() {
        return passengerRepository.getAllLongestNames();
    }
    @Override
    public Map<Long, Integer> getCountPassengerByTrip() {
        return passengerRepository.getCountPassengerByTrip();
    }

    @Override
    public Map<String, Boolean> delete(Long id) {
        return passengerRepository.delete(id);
    }
}
