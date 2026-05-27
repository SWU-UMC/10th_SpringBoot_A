package com.example.umc10th.domain.member.dto;

import com.example.umc10th.domain.member.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public class MemberReqDto {

    public record Create(
            @NotBlank @Size(max = 20) String name,
            @NotNull Gender gender,
            @NotNull LocalDate birthDate,
            @NotBlank @Size(max = 255) String address,
            @NotBlank @Size(max = 20) String nickname
    ) {
    }

    public record GetMyPageRequest(
            @NotNull @Positive Long id
    ) {
    }

    public record SignUpRequest(
            @NotBlank @Size(max = 20) String name,
            @NotNull Gender gender,
            @NotNull LocalDate birthDate,
            @NotBlank @Size(max = 255) String address,
            @NotBlank @Size(max = 20) String nickname,
            @NotBlank @Email @Size(max = 100) String email,
            @NotBlank @Size(min = 8, max = 30) String password,
            @NotEmpty List<@NotNull @Positive Long> preferCategoryIds
    ) {
    }

    public record LoginRequest(
            @NotBlank @Email @Size(max = 100) String email,
            @NotBlank String password
    ) {
    }
}
