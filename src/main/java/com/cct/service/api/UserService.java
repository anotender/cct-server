package com.cct.service.api;

import com.cct.model.dto.UserDTO;

import java.util.Collection;

public interface UserService {
    UserDTO getUser(String id);

    Collection<UserDTO> getUsers();

    UserDTO save(UserDTO userDTO);
}
