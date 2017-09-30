package com.cct.service.api;

import com.cct.model.dto.UserDTO;

import java.util.Collection;

public interface UserService {
    UserDTO getUser(Long id);

    UserDTO getUser(String email);

    Collection<UserDTO> getUsers();

    UserDTO save(UserDTO userDTO);
}
