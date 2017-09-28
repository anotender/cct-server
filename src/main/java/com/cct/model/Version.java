package com.cct.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
@EqualsAndHashCode(exclude = {"model", "cars"})
public class Version {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false)
    private Model model;

    @OneToMany(fetch = LAZY, mappedBy = "version")
    private Set<Car> cars = new HashSet<>();

    @Column(nullable = false)
    private String years;
    private String fuel;
    private Double cityFuelConsumption;
    private Double highwayFuelConsumption;
    private Double mixedFuelConsumption;
}
