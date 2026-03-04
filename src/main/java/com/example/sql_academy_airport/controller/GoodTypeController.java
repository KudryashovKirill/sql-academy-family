package com.example.sql_academy_airport.controller;

import com.example.sql_academy_airport.dto.input.GoodTypeInputDto;
import com.example.sql_academy_airport.dto.output.GoodTypeOutputDto;
import com.example.sql_academy_airport.service.GoodTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/goodType")
public class GoodTypeController {
    private GoodTypeService goodTypeService;

    @Autowired
    public GoodTypeController(GoodTypeService goodTypeService) {
        this.goodTypeService = goodTypeService;
    }

    @PostMapping
    public ResponseEntity<GoodTypeOutputDto> create(@RequestBody GoodTypeInputDto goodTypeInputDto) {
        return new ResponseEntity<>(goodTypeService.create(goodTypeInputDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoodTypeOutputDto> getById(@PathVariable Long id) {
        return new ResponseEntity<>(goodTypeService.getById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoodTypeOutputDto> update(@RequestBody GoodTypeInputDto goodTypeInputDto,
                                                    @PathVariable Long id) {
        return new ResponseEntity<>(goodTypeService.update(goodTypeInputDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable Long id) {
        return new ResponseEntity<>(goodTypeService.delete(id), HttpStatus.OK);
    }
}
