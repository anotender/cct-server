package com.cct.repository.api;

import com.cct.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findOneById(String id);

    Optional<User> findOneByEmail(String email);
}
