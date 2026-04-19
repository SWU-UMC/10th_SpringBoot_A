package com.example.umc10th.domain.user.service;

import com.example.umc10th.domain.user.dto.UserRequestDto;
import com.example.umc10th.domain.user.dto.UserResponseDto;

public interface UserService {

    // Query Parameter
    public String singleParameter(String singleParameter);

    // Request Body
    public UserResponseDto.RequestBody requestBody(UserRequestDto.RequestBody dto);
}
