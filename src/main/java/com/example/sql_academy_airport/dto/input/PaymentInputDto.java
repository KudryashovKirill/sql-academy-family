package com.example.sql_academy_airport.dto.input;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class PaymentInputDto {
    @NotNull
    @Min(value = 1, message = "amount can`t be less than 0")
    Integer amount;
    @Min(value = 1, message = "unitPrice can`t be less than 0")
    Integer unitPrice;
    LocalDate date;
    Long familyMemberId;
    Long goodId;
}
