package com.cct.service.impl;

import com.cct.model.User;
import com.cct.model.dto.UserDTO;
import com.cct.repository.UserRepository;
import com.cct.service.api.UserService;
import com.cct.util.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Collection<UserDTO> getUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(modelMapper::convertToDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public UserDTO getUser(String id) {
        return userRepository
                .findOneById(id)
                .map(modelMapper::convertToDTO)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        User user = modelMapper.convertToEntity(userDTO);
        return modelMapper.convertToDTO(userRepository.save(user));
    }
}
