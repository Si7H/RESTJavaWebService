package com.deadlifter.service.impl;

import com.deadlifter.UserRepository;
import com.deadlifter.io.entity.UserEntity;
import com.deadlifter.service.UserService;
import com.deadlifter.shared.Utils;
import com.deadlifter.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Utils utils;

    public UserServiceImpl(UserRepository userRepository, Utils utils) {
        this.userRepository = userRepository;
        this.utils = utils;
    }

    @Override
    public UserDto createUser(UserDto user) {

        if(userRepository.findByEmail(user.getEmail()) != null) throw new RuntimeException("Record already exists");

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        userEntity.setEncryptedPassword("test");

        String publicUserId = utils.generateUserId(30);
        userEntity.setUserId(publicUserId);

        UserEntity storedUserDetails = userRepository.save(userEntity);

        UserDto returnedValue = new UserDto();
        BeanUtils.copyProperties(storedUserDetails, returnedValue);

        return returnedValue;
    }
}
