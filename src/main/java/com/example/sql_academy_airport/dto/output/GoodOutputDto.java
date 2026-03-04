package com.example.sql_academy_airport.dto.output;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GoodOutputDto {
    Long goodId;
    String goodName;
    GoodTypeOutputDto type;
}
