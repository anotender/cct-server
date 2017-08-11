package com.cct.model;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "users")
@TypeAlias("user")
public class User {
    @Id
    private UUID id;
    @Email
    private String email;
    private String password;
}
