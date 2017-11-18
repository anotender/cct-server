package com.cct.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class FuelPrice {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Fuel fuel;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String fuelStationId;
}
