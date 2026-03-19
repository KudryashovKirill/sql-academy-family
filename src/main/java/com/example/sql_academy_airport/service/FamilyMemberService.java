package com.example.sql_academy_airport.service;

import com.example.sql_academy_airport.dto.input.FamilyMemberInputDto;
import com.example.sql_academy_airport.dto.output.FamilyMemberOutputDto;
import com.example.sql_academy_airport.dto.output.SpendsOnFun;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FamilyMemberService {
    FamilyMemberOutputDto create(FamilyMemberInputDto familyMember);

    FamilyMemberOutputDto getById(Long id);

    FamilyMemberOutputDto update(FamilyMemberInputDto familyMember, Long id);

    Map<String, Boolean> delete(Long id);

    Set<String> getOldestMember();

    Set<String> getStatusByGoodType(String goodType);

    List<SpendsOnFun> getSpendsOnFun();

    Set<String> getProductsOneMoreTime();

    Set<String> getAllByStatus(String status);

    Map<String, Integer> getSpendsByDate(LocalDate timeStart, LocalDate timeEnd);
}
