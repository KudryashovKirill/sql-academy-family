package com.example.sql_academy_airport.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "goods")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "good_id")
    Long goodId;
    @Column(name = "good_name", nullable = false, length = 100)
    String goodName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type", nullable = false)
    GoodType type;
    @OneToMany(mappedBy = "good")
    List<Payment> payments = new ArrayList<>();

    public Good(Long goodId, String goodName, GoodType type) {
        this.goodId = goodId;
        this.goodName = goodName;
        this.type = type;
    }

    public Good(long goodId, String goodName) {

    }
}
