package com.cct.model;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
public class Version {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false)
    private Model model;

    @Column(nullable = false)
    private Integer startYear;
    private Integer endYear;
    private String fuel;
    private Double cityFuelConsumption;
    private Double highwayFuelConsumption;
    private Double mixedFuelConsumption;
}
