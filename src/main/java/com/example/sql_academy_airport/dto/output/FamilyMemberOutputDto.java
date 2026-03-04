package com.example.sql_academy_airport.dto.output;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FamilyMemberOutputDto {
    Long memberId;
    String status;
    String memberName;
    LocalDate birthday;
}
