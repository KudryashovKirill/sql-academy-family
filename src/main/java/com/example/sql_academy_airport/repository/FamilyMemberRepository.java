package com.example.sql_academy_airport.repository;

import com.example.sql_academy_airport.dto.output.SpendsOnFun;
import com.example.sql_academy_airport.model.FamilyMember;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FamilyMemberRepository {
    FamilyMember create(FamilyMember familyMember);

    FamilyMember getById(Long id);

    FamilyMember update(FamilyMember familyMember, Long id);

    Map<String, Boolean> delete(Long id);

    Set<String> getOldestMember();

    Set<String> getStatusByGoodType(String goodType);

    List<SpendsOnFun> getSpendsOnFun();

    Set<String> getProductsOneMoreTime();

    Set<String> getAllByStatus(String status);

    Map<String, Integer> getSpendsByDate(LocalDate timeStart, LocalDate timeEnd);
}
