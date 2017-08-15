package com.cct.util;

import com.cct.model.Make;
import com.cct.model.User;
import com.cct.model.dto.MakeDTO;
import com.cct.model.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class ModelMapper {

    public User convertToEntity(UserDTO userDTO) {
        User user = new User();

        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        return user;
    }

    public UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());

        return userDTO;
    }

    public Make convertToEntity(MakeDTO makeDTO) {
        Make make = new Make();

        make.setId(makeDTO.getId());
        make.setName(makeDTO.getName());

        return make;
    }

    public MakeDTO convertToDTO(Make make) {
        MakeDTO makeDTO = new MakeDTO();

        makeDTO.setId(make.getId());
        makeDTO.setName(make.getName());

        return makeDTO;
    }

}
