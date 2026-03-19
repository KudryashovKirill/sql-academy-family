package com.example.sql_academy_airport.service;

import com.example.sql_academy_airport.FamilyMemberMapper;
import com.example.sql_academy_airport.PaymentMapper;
import com.example.sql_academy_airport.dto.input.FamilyMemberInputDto;
import com.example.sql_academy_airport.dto.output.FamilyMemberOutputDto;
import com.example.sql_academy_airport.dto.output.SpendsOnFun;
import com.example.sql_academy_airport.model.FamilyMember;
import com.example.sql_academy_airport.repository.FamilyMemberRepository;
import com.example.sql_academy_airport.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class FamilyMemberServiceImpl implements FamilyMemberService {
    private final FamilyMemberRepository familyMemberRepository;
    private final PaymentRepository paymentRepository;
    private final FamilyMemberMapper familyMemberMapper;
    private final PaymentMapper paymentMapper;

    @Autowired
    public FamilyMemberServiceImpl(
            FamilyMemberRepository familyMemberRepository,
            PaymentRepository paymentRepository,
            FamilyMemberMapper familyMemberMapper,
            PaymentMapper paymentMapper) {

        this.familyMemberRepository = familyMemberRepository;
        this.paymentRepository = paymentRepository;
        this.familyMemberMapper = familyMemberMapper;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public FamilyMemberOutputDto getById(Long id) {
        FamilyMember member = familyMemberRepository.getById(id);
        return familyMemberMapper.toDto(member);
    }

    @Override
    public FamilyMemberOutputDto create(FamilyMemberInputDto familyMember) {
        FamilyMember member = familyMemberMapper.toEntity(familyMember);
        return familyMemberMapper.toDto(familyMemberRepository.create(member));
    }

    @Override
    public FamilyMemberOutputDto update(FamilyMemberInputDto familyMember, Long id) {
        FamilyMember member = familyMemberRepository.getById(id);
        familyMemberMapper.updateEntityFromDto(familyMember, member);
        FamilyMember updated = familyMemberRepository.update(member, id);
        return familyMemberMapper.toDto(updated);
    }

    @Override
    public Set<String> getOldestMember() {
        return familyMemberRepository.getOldestMember();
    }

    @Override
    public Set<String> getStatusByGoodType(String goodType) {
        return familyMemberRepository.getStatusByGoodType(goodType);
    }

    @Override
    public List<SpendsOnFun> getSpendsOnFun() {
        return familyMemberRepository.getSpendsOnFun();
    }

    @Override
    public Set<String> getProductsOneMoreTime() {
        return familyMemberRepository.getProductsOneMoreTime();
    }

    @Override
    public Set<String> getAllByStatus(String status) {
        return familyMemberRepository.getAllByStatus(status);
    }

    @Override
    public Map<String, Integer> getSpendsByDate(LocalDate timeStart, LocalDate timeEnd) {
        return familyMemberRepository.getSpendsByDate(timeStart, timeEnd);
    }

    @Override
    public Map<String, Boolean> delete(Long id) {
        return familyMemberRepository.delete(id);
    }
}
