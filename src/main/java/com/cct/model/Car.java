package com.cct.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
@EqualsAndHashCode(exclude = {"fuelRefills"})
public class Car {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn
    private Version version;

    @ManyToOne(fetch = LAZY)
    @JoinColumn
    private User user;

    @Column(nullable = false)
    private String name;

    @OneToMany(fetch = LAZY, mappedBy = "car")
    private Set<FuelRefill> fuelRefills = new HashSet<>();
}
