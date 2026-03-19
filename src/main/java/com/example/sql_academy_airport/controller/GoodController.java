package com.example.sql_academy_airport.controller;

import com.example.sql_academy_airport.dto.input.GoodInputDto;
import com.example.sql_academy_airport.dto.output.GoodOutputDto;
import com.example.sql_academy_airport.service.GoodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/good")
public class GoodController {
    private GoodService goodService;

    public GoodController(GoodService goodService) {
        this.goodService = goodService;
    }

    @PostMapping
    public ResponseEntity<GoodOutputDto> create(@RequestBody GoodInputDto goodInputDto) {
        return new ResponseEntity<>(goodService.create(goodInputDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoodOutputDto> getById(@PathVariable Long id) {
        return new ResponseEntity<>(goodService.getById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoodOutputDto> update(@RequestBody GoodInputDto goodInputDto,
                                                @PathVariable Long id) {
        return new ResponseEntity<>(goodService.update(goodInputDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable Long id) {
        return new ResponseEntity<>(goodService.delete(id), HttpStatus.OK);
    }

    @GetMapping("/getMostExp/{goodTypeName}/{limit}")
    public ResponseEntity<Map<String, Integer>> getMostExpensiveGood(@PathVariable String goodTypeName,
                                                                     @PathVariable Integer limit) {
        return new ResponseEntity<>(goodService.getMostExpensiveGood(goodTypeName, limit), HttpStatus.OK);
    }
}
