package com.example.sql_academy_airport.service;

import com.example.sql_academy_airport.dto.input.PaymentInputDto;
import com.example.sql_academy_airport.dto.output.PaymentOutputDto;
import com.example.sql_academy_airport.model.FamilyMember;
import com.example.sql_academy_airport.model.Good;
import com.example.sql_academy_airport.model.Payment;
import com.example.sql_academy_airport.repository.FamilyMemberRepository;
import com.example.sql_academy_airport.repository.GoodRepository;
import com.example.sql_academy_airport.repository.PaymentRepository;
import com.example.sql_academy_airport.PaymentMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final FamilyMemberRepository familyMemberRepository;
    private final GoodRepository goodRepository;
    private final PaymentMapper paymentMapper;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              FamilyMemberRepository familyMemberRepository,
                              GoodRepository goodRepository,
                              PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.familyMemberRepository = familyMemberRepository;
        this.goodRepository = goodRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public PaymentOutputDto getById(Long id) {
        Payment payment = paymentRepository.getById(id);
        return paymentMapper.toDto(payment);
    }

    @Override
    public PaymentOutputDto create(PaymentInputDto dto) {
        Payment payment = paymentMapper.toEntity(dto);
        FamilyMember member = familyMemberRepository.getById(dto.getFamilyMemberId());
        Good good = goodRepository.getById(dto.getGoodId());

        payment.setFamilyMember(member);
        payment.setGood(good);
        return paymentMapper.toDto(paymentRepository.create(payment));
    }

    @Override
    public PaymentOutputDto update(PaymentInputDto dto, Long id) {
        Payment existing = paymentRepository.getById(id);
        paymentMapper.updateEntityFromDto(dto, existing);

        return paymentMapper.toDto(paymentRepository.update(existing, id));
    }

    @Override
    public Map<String, Boolean> delete(Long id) {
        return paymentRepository.delete(id);
    }
}
