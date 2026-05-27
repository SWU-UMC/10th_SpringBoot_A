package com.example.umc10th.domain.member.dto;

import com.example.umc10th.domain.member.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
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
            @Schema(description = "회원 이름", example = "김철수")
            @NotBlank @Size(max = 20) String name,

            @Schema(description = "성별", example = "MALE", allowableValues = {"MALE", "FEMALE", "NONE"})
            @NotNull Gender gender,

            @Schema(description = "생년월일", example = "2000-03-24")
            @NotNull LocalDate birthDate,

            @Schema(description = "주소", example = "서울특별시 성동구 왕십리로 222")
            @NotBlank @Size(max = 255) String address,

            @Schema(description = "닉네임", example = "bean")
            @NotBlank @Size(max = 20) String nickname
    ) {
    }

    public record GetMyPageRequest(
            @Schema(description = "회원 ID", example = "1")
            @NotNull @Positive Long id
    ) {
    }

    public record SignUpRequest(
            @Schema(description = "회원 이름", example = "김철수")
            @NotBlank @Size(max = 20) String name,

            @Schema(description = "성별", example = "MALE", allowableValues = {"MALE", "FEMALE", "NONE"})
            @NotNull Gender gender,

            @Schema(description = "생년월일", example = "2000-03-24")
            @NotNull LocalDate birthDate,

            @Schema(description = "주소", example = "서울특별시 성동구 왕십리로 222")
            @NotBlank @Size(max = 255) String address,

            @Schema(description = "닉네임", example = "bean")
            @NotBlank @Size(max = 20) String nickname,

            @Schema(description = "이메일", example = "bean@example.com")
            @NotBlank @Email @Size(max = 100) String email,

            @Schema(description = "비밀번호", example = "password123")
            @NotBlank @Size(min = 8, max = 30) String password,

            @Schema(description = "선호 음식 카테고리 ID 목록", example = "[1, 2]")
            @NotEmpty List<@NotNull @Positive Long> preferCategoryIds
    ) {
    }

    public record LoginRequest(
            @Schema(description = "이메일", example = "bean@example.com")
            @NotBlank @Email @Size(max = 100) String email,

            @Schema(description = "비밀번호", example = "password123")
            @NotBlank String password
    ) {
    }
}
