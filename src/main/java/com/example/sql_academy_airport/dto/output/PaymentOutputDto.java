package com.example.sql_academy_airport.dto.output;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentOutputDto {
    Long paymentId;
    Integer amount;
    Integer unitPrice;
    LocalDate date;
    FamilyMemberOutputDto familyMember;
    GoodOutputDto good;
}
