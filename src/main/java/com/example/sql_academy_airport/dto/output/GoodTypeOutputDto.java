package com.example.sql_academy_airport.dto.output;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GoodTypeOutputDto {
    Long goodTypeId;
    String goodTypeName;
}
