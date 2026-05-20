package com.example.umc10th.domain.user.converter;

import com.example.umc10th.domain.user.dto.request.UserRequest;
import com.example.umc10th.domain.user.dto.response.MyPageResponse;
import com.example.umc10th.domain.user.dto.response.UserResponse;
import com.example.umc10th.domain.user.entity.User;

public class UserConverter {

    private UserConverter() {
    }

    public static User toUser(UserRequest request, String encodedPassword) {
        return User.builder()
                .name(request.name())
                .email(request.email())
                .password(encodedPassword)
                .gender(request.gender())
                .address(request.address())
                .point(0L)
                .build();
    }

    public static UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(), user.getCreatedAt());
    }

    public static MyPageResponse toMyPageResponse(
            User user,
            long challengingCount,
            long completedCount,
            long reviewCount
    ) {
        return new MyPageResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getGender(),
                user.getAddress(),
                user.getPhoneNumber(),
                user.getPoint(),
                challengingCount,
                completedCount,
                reviewCount
        );
    }
}
