package com.example.sql_academy_airport.controller;

import com.example.sql_academy_airport.dto.CompanyDto;
import com.example.sql_academy_airport.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/company")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<CompanyDto> create(@RequestBody CompanyDto companyDto) {
        return new ResponseEntity<>(companyService.create(companyDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getById(@PathVariable Long id) {
        return new ResponseEntity<>(companyService.getById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDto> update(@RequestBody CompanyDto companyDto,
                                             @PathVariable Long id) {
        return new ResponseEntity<>(companyService.update(companyDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable Long id) {
        return new ResponseEntity<>(companyService.delete(id), HttpStatus.OK);
    }

    @GetMapping("/allNames")
    public ResponseEntity<List<String>> getAllNames() {
        return new ResponseEntity<>(companyService.getAllNames(), HttpStatus.OK);
    }

    @GetMapping("/plane")
    public ResponseEntity<List<String>> getCompaniesByPlaneName(@RequestParam String planeName) {
        return new ResponseEntity<>(companyService.getCompaniesByPlaneName(planeName), HttpStatus.OK);
    }

    @GetMapping("/townFrom")
    public ResponseEntity<List<String>> getAllTownFrom(@RequestParam String townFrom) {
        return new ResponseEntity<>(companyService.getAllTownFrom(townFrom), HttpStatus.OK);
    }
}
