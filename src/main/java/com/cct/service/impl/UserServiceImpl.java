package com.cct.service.impl;

import com.cct.exception.BadRequestException;
import com.cct.model.User;
import com.cct.model.dto.UserDTO;
import com.cct.repository.api.UserRepository;
import com.cct.service.api.UserService;
import com.cct.util.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.cct.exception.ErrorInfo.USER_NOT_FOUND;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO getUser(Long id) {
        return userRepository
                .findOneById(id)
                .map(ModelMapper::convertToDTO)
                .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));
    }

    @Override
    public UserDTO getUser(String email) {
        return userRepository
                .findOneByEmail(email)
                .map(ModelMapper::convertToDTO)
                .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));
    }

    @Override
    public Collection<UserDTO> getUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(ModelMapper::convertToDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        User user = ModelMapper.convertToEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ModelMapper.convertToDTO(userRepository.save(user));
    }
}
