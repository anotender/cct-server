package com.cct.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
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

    public Rating(Long id) {
        this.id = id;
    }
}
