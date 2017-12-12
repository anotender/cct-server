package com.cct.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"cars", "ratings"})
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(fetch = LAZY, mappedBy = "user")
    private Set<Car> cars = new HashSet<>();

    @OneToMany(fetch = LAZY, mappedBy = "user")
    private Set<Rating> ratings = new HashSet<>();

    public User(Long id) {
        this.id = id;
    }
}
