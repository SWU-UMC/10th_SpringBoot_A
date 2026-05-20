package com.example.umc10th.domain.user.service;

import com.example.umc10th.domain.user.dto.request.LoginRequest;
import com.example.umc10th.domain.user.dto.request.UserRequest;
import com.example.umc10th.domain.user.dto.response.MyPageResponse;
import com.example.umc10th.domain.user.dto.response.TokenResponse;
import com.example.umc10th.domain.user.dto.response.UserResponse;

public interface UserService {

    UserResponse signUp(UserRequest request);

    TokenResponse login(LoginRequest request);

    MyPageResponse getMyPage(Long userId);
}
