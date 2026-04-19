package com.example.umc10th.domain.user.service;

import com.example.umc10th.domain.user.converter.UserConverter;
import com.example.umc10th.domain.user.dto.UserRequestDto;
import com.example.umc10th.domain.user.dto.UserResponseDto;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public String singleParameter(String singleParameter) {
        return singleParameter;
    }

    @Override
    public UserResponseDto.RequestBody requestBody(UserRequestDto.RequestBody dto) {
        return UserConverter.toRequestBody(dto.stringTest(), dto.longTest());
    }
}