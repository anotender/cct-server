package com.cct.service.impl;

import com.cct.exception.BadRequestException;
import com.cct.model.User;
import com.cct.model.dto.UserDTO;
import com.cct.repository.api.UserRepository;
import com.cct.service.api.UserService;
import com.cct.util.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.cct.exception.ErrorInfo.USER_NOT_FOUND;

@Service
@Transactional
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
    public UserDTO getUser(Long id) {
        return userRepository
                .findOneById(id)
                .map(modelMapper::convertToDTO)
                .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        User user = modelMapper.convertToEntity(userDTO);
        return modelMapper.convertToDTO(userRepository.save(user));
    }
}
