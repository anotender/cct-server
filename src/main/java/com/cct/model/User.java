package com.cct.model;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;

@Data
@Document(collection = "users")
@TypeAlias("user")
public class User {
    @Id
    @Field("id")
    private String id;

    @Email
    @Indexed(unique = true)
    @Field("email")
    private String email;

    @NotNull
    @Field("password")
    private String password;
}
