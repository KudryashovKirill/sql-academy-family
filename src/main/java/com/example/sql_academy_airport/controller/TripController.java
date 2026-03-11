package com.example.sql_academy_airport.controller;

import com.example.sql_academy_airport.dto.TripDto;
import com.example.sql_academy_airport.service.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("trip")
public class TripController {
    private TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping
    public ResponseEntity<TripDto> create(@RequestBody TripDto tripDto) {
        return new ResponseEntity<>(tripService.create(tripDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripDto> getById(@PathVariable Long id) {
        return new ResponseEntity<>(tripService.getById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TripDto> update(@RequestBody TripDto tripDto,
                                          @PathVariable Long id) {
        return new ResponseEntity<>(tripService.update(tripDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable Long id) {
        return new ResponseEntity<>(tripService.delete(id), HttpStatus.OK);
    }

    @GetMapping("/town/{townFrom}")
    public ResponseEntity<List<TripDto>> getAllFromTownFrom(@PathVariable String townFrom) {
        return new ResponseEntity<>(tripService.getAllFromTownFrom(townFrom), HttpStatus.OK);
    }

    @GetMapping("/plane/name/{name}")
    public Integer getAllPlanesByName(@PathVariable String name) {
        return tripService.getAllPlanesByName(name);
    }

    @GetMapping("/plane/town/{townTo}")
    public ResponseEntity<List<String>> getAllPlanesByTownTo(@PathVariable String townTo) {
        return new ResponseEntity<>(tripService.getAllPlanesByTownTo(townTo), HttpStatus.OK);
    }

    @GetMapping("/plane/all/{townFrom}")
    public ResponseEntity<Map<String, Duration>> getAllFromTownFromMap(@PathVariable String townFrom) {
        return new ResponseEntity<>(tripService.getAllFromTownFromMap(townFrom), HttpStatus.OK);
    }

    @GetMapping("/{timeStart}/{timeEnd}")
    public ResponseEntity<List<TripDto>> getAllBetweenTime(@PathVariable LocalDate timeStart,
                                                           @PathVariable LocalDate timeEnd) {
        return new ResponseEntity<>(tripService.getAllBetweenTime(timeStart, timeEnd), HttpStatus.OK);
    }
}
