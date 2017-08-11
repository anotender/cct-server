package com.cct.service.impl;

import com.cct.model.User;
import com.cct.service.api.UserService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public Collection<User> getUsers() {
        User u1 = new User();
        u1.setId(UUID.randomUUID());
        u1.setEmail("u1@email.com");
        u1.setPassword("123");

        User u2 = new User();
        u2.setId(UUID.randomUUID());
        u2.setEmail("u2@email.com");
        u2.setPassword("123");

        return Stream.of(u1, u2).collect(Collectors.toSet());
    }

}
