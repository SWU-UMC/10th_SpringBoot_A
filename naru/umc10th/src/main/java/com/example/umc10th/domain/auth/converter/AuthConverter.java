package com.example.umc10th.domain.auth.converter;

import com.example.umc10th.domain.auth.dto.AuthRequestDto;
import com.example.umc10th.domain.auth.dto.AuthResponseDto;
import com.example.umc10th.domain.user.entity.User;
import com.example.umc10th.domain.user.entity.enums.Role;
import com.example.umc10th.domain.user.entity.enums.Status;

public class AuthConverter {

    public static User toUser(AuthRequestDto.SignUpDto request, String encodedPassword) {
        return User.builder()
                .email(request.email())
                .password(encodedPassword)
                .name(request.name())
                .role(Role.USER)
                .status(Status.ACTIVE)
                .step(4)
                .totalPoint(0)
                .gender(request.gender())
                .birth(request.birth())
                .baseAddress(request.address())
                .isVerified(false)
                .build();
    }

    public static AuthResponseDto.SignUpResultDto toSignUpResultDto(User user) {
        return AuthResponseDto.SignUpResultDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
