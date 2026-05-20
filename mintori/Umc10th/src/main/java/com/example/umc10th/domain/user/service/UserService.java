package com.example.umc10th.domain.user.service;

import com.example.umc10th.domain.user.dto.request.UserRequest;
import com.example.umc10th.domain.user.dto.response.MyPageResponse;
import com.example.umc10th.domain.user.dto.response.UserResponse;

public interface UserService {

    /** 회원가입 */
    UserResponse signUp(UserRequest request);

    /** 마이페이지 조회 */
    MyPageResponse getMyPage(Long userId);
}
