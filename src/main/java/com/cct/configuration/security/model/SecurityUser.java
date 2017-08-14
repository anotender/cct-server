package com.cct.configuration.security.model;

import com.cct.model.User;
import org.springframework.security.core.authority.AuthorityUtils;

public class SecurityUser extends org.springframework.security.core.userdetails.User {
    private User user;

    public SecurityUser(User user) {
        super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList());
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
