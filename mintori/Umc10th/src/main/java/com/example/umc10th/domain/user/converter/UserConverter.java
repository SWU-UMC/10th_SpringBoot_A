package com.example.umc10th.domain.user.converter;

import com.example.umc10th.domain.user.dto.request.UserRequest;
import com.example.umc10th.domain.user.dto.response.UserResponse;
import com.example.umc10th.domain.user.entity.User;

public class UserConverter {

    private UserConverter() {
    }

    public static User toUser(UserRequest request) {
        return User.builder()
                .name(request.name())
                .email(request.email())
                .password(request.password())
                .gender(request.gender())
                .address(request.address())
                .point(0L)
                .build();
    }

    public static UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(), user.getCreatedAt());
    }
}
