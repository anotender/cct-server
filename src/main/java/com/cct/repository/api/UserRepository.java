package com.cct.repository.api;

import com.cct.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneById(Long id);

    Optional<User> findOneByEmail(String email);
}
