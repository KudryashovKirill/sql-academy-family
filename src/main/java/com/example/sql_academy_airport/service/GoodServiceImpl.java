package com.example.sql_academy_airport.service;

import com.example.sql_academy_airport.GoodMapper;
import com.example.sql_academy_airport.dto.input.GoodInputDto;
import com.example.sql_academy_airport.dto.output.GoodOutputDto;
import com.example.sql_academy_airport.model.Good;
import com.example.sql_academy_airport.model.GoodType;
import com.example.sql_academy_airport.repository.GoodRepository;
import com.example.sql_academy_airport.repository.GoodTypeRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GoodServiceImpl implements GoodService {
    private final GoodRepository goodRepository;
    private final GoodTypeRepository goodTypeRepository;
    private final GoodMapper goodMapper;

    public GoodServiceImpl(GoodRepository goodRepository,
                           GoodTypeRepository goodTypeRepository,
                           GoodMapper goodMapper) {
        this.goodRepository = goodRepository;
        this.goodTypeRepository = goodTypeRepository;
        this.goodMapper = goodMapper;
    }

    @Override
    public GoodOutputDto getById(Long id) {

        Good good = goodRepository.getById(id);

        return goodMapper.toDto(good);
    }

    @Override
    public GoodOutputDto create(GoodInputDto dto) {
        Good good = goodMapper.toEntity(dto);
        GoodType type = goodTypeRepository.getById(dto.getGoodType());
        good.setType(type);

        Good saved = goodRepository.create(good);

        return goodMapper.toDto(saved);
    }

    @Override
    public GoodOutputDto update(GoodInputDto goodInputDto, Long id) {
        Good typeInTable = goodRepository.getById(id);
        goodMapper.updateEntityFromDto(goodInputDto, typeInTable);
        return goodMapper.toDto(goodRepository.update(typeInTable, id));
    }

    @Override
    public Map<String, Integer> getMostExpensiveGood(String goodTypeName, Integer limit) {
        return goodRepository.getMostExpensiveGood(goodTypeName, limit);
    }

    @Override
    public Map<String, Boolean> delete(Long id) {
        return goodRepository.delete(id);
    }
}
