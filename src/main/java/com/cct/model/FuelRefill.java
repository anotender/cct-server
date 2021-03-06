package com.cct.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
public class FuelRefill {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Double liters;

    @Column(nullable = false)
    private Double distance;

    @Column(nullable = false)
    private Double averageFuelConsumption;

    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false)
    private Car car;

    @OneToOne(fetch = LAZY)
    @JoinColumn
    private FuelPrice fuelPrice;
}
