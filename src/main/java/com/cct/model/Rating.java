package com.cct.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
public class Rating {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Integer points;

    private String comment;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false)
    private Version version;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false)
    private User user;
}
