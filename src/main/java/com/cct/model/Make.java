package com.cct.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"models"})
public class Make {
    @Id
    private String id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String logoUrl;

    @OneToMany(fetch = LAZY, mappedBy = "make")
    private Set<Model> models = new HashSet<>();
}
