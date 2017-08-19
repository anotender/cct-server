package com.cct.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"make", "versions", "cars"})
public class Model {
    @Id
    private String id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false)
    private Make make;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String body;

    @OneToMany(fetch = LAZY, mappedBy = "model")
    private Set<Version> versions = new HashSet<>();

    @OneToMany(fetch = LAZY, mappedBy = "model")
    private Set<Car> cars = new HashSet<>();
}
