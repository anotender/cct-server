package com.cct.repository;

import com.cct.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findOneById(String id);

    Optional<User> findOneByEmail(String email);
}
