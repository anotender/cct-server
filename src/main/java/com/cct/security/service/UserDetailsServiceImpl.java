package com.cct.security.service;

import com.cct.exception.BadRequestException;
import com.cct.repository.UserRepository;
import com.cct.security.model.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import static com.cct.exception.ErrorInfo.USER_NOT_FOUND;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findOneByEmail(email)
                .map(SecurityUser::new)
                .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));
    }
}
