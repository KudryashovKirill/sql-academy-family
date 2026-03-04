package com.example.sql_academy_airport.controller;

import com.example.sql_academy_airport.dto.input.FamilyMemberInputDto;
import com.example.sql_academy_airport.dto.output.FamilyMemberOutputDto;
import com.example.sql_academy_airport.service.FamilyMemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
}
