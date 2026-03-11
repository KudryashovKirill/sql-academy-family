package com.example.sql_academy_airport.controller;

import com.example.sql_academy_airport.dto.PassengerDto;
import com.example.sql_academy_airport.service.PassengerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/passenger")
public class PassengerController {
    private PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PostMapping
    public ResponseEntity<PassengerDto> create(@RequestBody PassengerDto passengerDto) {
        return new ResponseEntity<>(passengerService.create(passengerDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassengerDto> getById(@PathVariable Long id) {
        return new ResponseEntity<>(passengerService.getById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PassengerDto> update(@RequestBody PassengerDto passengerDto,
                                               @PathVariable Long id) {
        return new ResponseEntity<>(passengerService.update(passengerDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable Long id) {
        return new ResponseEntity<>(passengerService.delete(id), HttpStatus.OK);
    }

    @GetMapping("/allNames")
    public ResponseEntity<List<String>> getAllNames() {
        return new ResponseEntity<>(passengerService.getAllNames(), HttpStatus.OK);
    }

    @GetMapping("/namePostfix")
    public ResponseEntity<List<String>> getAllEndWith(@RequestParam String namePostfix) {
        return new ResponseEntity<>(passengerService.getAllEndWith(namePostfix), HttpStatus.OK);
    }

    @GetMapping("/longestNames")
    public ResponseEntity<List<String>> getAllLongestNames() {
        return new ResponseEntity<>(passengerService.getAllLongestNames(), HttpStatus.OK);
    }

    @GetMapping("/countByTrip")
    public ResponseEntity<Map<Long, Integer>> getCountPassengerByTrip() {
        return new ResponseEntity<>(passengerService.getCountPassengerByTrip(), HttpStatus.OK);
    }
}
