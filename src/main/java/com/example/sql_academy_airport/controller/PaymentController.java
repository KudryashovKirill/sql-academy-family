package com.example.sql_academy_airport.controller;

import com.example.sql_academy_airport.dto.input.PaymentInputDto;
import com.example.sql_academy_airport.dto.output.PaymentOutputDto;
import com.example.sql_academy_airport.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentOutputDto> create(@RequestBody PaymentInputDto paymentInputDto) {
        return new ResponseEntity<>(paymentService.create(paymentInputDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentOutputDto> getById(@PathVariable Long id) {
        return new ResponseEntity<>(paymentService.getById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentOutputDto> update(@RequestBody PaymentInputDto paymentInputDto,
                                                   @PathVariable Long id) {
        return new ResponseEntity<>(paymentService.update(paymentInputDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable Long id) {
        return new ResponseEntity<>(paymentService.delete(id), HttpStatus.OK);
    }
}
