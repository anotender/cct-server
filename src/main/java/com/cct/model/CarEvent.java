package com.cct.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
public class CarEvent {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private Double price;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false)
    private Car car;
}
