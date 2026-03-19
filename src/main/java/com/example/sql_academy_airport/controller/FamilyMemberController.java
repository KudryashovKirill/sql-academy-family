package com.example.sql_academy_airport.controller;

import com.example.sql_academy_airport.dto.input.FamilyMemberInputDto;
import com.example.sql_academy_airport.dto.output.FamilyMemberOutputDto;
import com.example.sql_academy_airport.dto.output.SpendsOnFun;
import com.example.sql_academy_airport.service.FamilyMemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/familyMember")
public class FamilyMemberController {
    private FamilyMemberService familyMemberService;

    public FamilyMemberController(FamilyMemberService familyMemberService) {
        this.familyMemberService = familyMemberService;
    }

    @PostMapping
    public ResponseEntity<FamilyMemberOutputDto> create(@RequestBody FamilyMemberInputDto familyMemberInputDto) {
        return new ResponseEntity<>(familyMemberService.create(familyMemberInputDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FamilyMemberOutputDto> getById(@PathVariable Long id) {
        return new ResponseEntity<>(familyMemberService.getById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FamilyMemberOutputDto> update(@RequestBody FamilyMemberInputDto familyMemberInputDto,
                                                        @PathVariable Long id) {
        return new ResponseEntity<>(familyMemberService.update(familyMemberInputDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable Long id) {
        return new ResponseEntity<>(familyMemberService.delete(id), HttpStatus.OK);
    }

    @GetMapping("/oldest")
    public ResponseEntity<Set<String>> getOldestMember() {
        return new ResponseEntity<>(familyMemberService.getOldestMember(), HttpStatus.OK);
    }

    @GetMapping("/status/{goodType}")
    public ResponseEntity<Set<String>> getStatusByGoodType(@PathVariable String goodType) {
        return new ResponseEntity<>(familyMemberService.getStatusByGoodType(goodType), HttpStatus.OK);
    }

    @GetMapping("/spendsOnFun")
    public ResponseEntity<List<SpendsOnFun>> getSpendsOnFun() {
        return new ResponseEntity<>(familyMemberService.getSpendsOnFun(), HttpStatus.OK);
    }

    @GetMapping("/productsOneMoreTime")
    public ResponseEntity<Set<String>> getProductsOneMoreTime() {
        return new ResponseEntity<>(familyMemberService.getProductsOneMoreTime(), HttpStatus.OK);
    }

    @GetMapping("/all/{status}")
    public ResponseEntity<Set<String>> getAllByStatus(@PathVariable String status) {
        return new ResponseEntity<>(familyMemberService.getAllByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/spends/{timeStart}/{timeEnd}")
    public ResponseEntity<Map<String, Integer>> getSpendsByDate(LocalDate timeStart, LocalDate timeEnd) {
        return new ResponseEntity<>(familyMemberService.getSpendsByDate(timeStart, timeEnd), HttpStatus.OK);
    }
}
