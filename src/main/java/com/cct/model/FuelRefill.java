package com.cct.model;

import lombok.Data;

import javax.persistence.*;

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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false)
    private Car car;
}
