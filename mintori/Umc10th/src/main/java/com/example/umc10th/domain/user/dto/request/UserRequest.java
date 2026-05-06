package com.example.umc10th.domain.user.dto.request;

import com.example.umc10th.domain.user.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequest(
        @NotBlank(message = "성함을 입력해주세요")
        String name,

        @NotBlank(message = "이메일을 입력해주세요")
        @Email
        String email,

        @NotBlank(message = "비밀번호를 입력해주세요")
        String password,

        @NotNull
        Gender gender,

        String address
        ) {
}
