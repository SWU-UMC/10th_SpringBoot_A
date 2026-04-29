package com.example.umc10th.domain.user.service;

import com.example.umc10th.domain.user.dto.UserRequestDto;
import com.example.umc10th.domain.user.dto.UserResponseDto;

public interface UserService {

    // Query Parameter
    String singleParameter(String singleParameter);

    // Request Body
    UserResponseDto.RequestBody requestBody(UserRequestDto.RequestBody dto);

    // 마이페이지 조회
    UserResponseDto.MyPageResultDto getMyPage(UserRequestDto.GetMyPageDto request);
}
