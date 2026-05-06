package com.example.umc10th.domain.user.controller;

import com.example.umc10th.domain.user.dto.request.UserRequest;
import com.example.umc10th.domain.user.dto.response.MyPageResponse;
import com.example.umc10th.domain.user.dto.response.UserResponse;
import com.example.umc10th.domain.user.service.UserService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.GeneralSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ApiResponse<UserResponse> signUp(@Valid @RequestBody UserRequest request) {
        return ApiResponse.onSuccess(GeneralSuccessCode.CREATED, userService.signUp(request));
    }

    @GetMapping("/{userId}/mypage")
    public ApiResponse<MyPageResponse> getMyPage(@PathVariable Long userId) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, userService.getMyPage(userId));
    }
}
