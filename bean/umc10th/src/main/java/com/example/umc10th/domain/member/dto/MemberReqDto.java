package com.example.umc10th.domain.member.dto;

import com.example.umc10th.domain.member.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

            @Schema(description = "비밀번호", example = "Password1!")
            @NotBlank
            @Size(min = 8, max = 30)
            @Pattern(
                    regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/]).+$",
                    message = "비밀번호는 영문, 숫자, 특수문자를 각각 1개 이상 포함해야 합니다."
            )
            String password,

            @Schema(description = "선호 음식 카테고리 ID 목록", example = "[1, 2]")
            @NotEmpty List<@NotNull @Positive Long> preferCategoryIds,

            @Schema(description = "약관 동의 정보")
            @Valid @NotNull TermsAgreement termsAgreement
    ) {
    }

    public record LoginRequest(
            @Schema(description = "이메일", example = "bean@example.com")
            @NotBlank @Email @Size(max = 100) String email,

            @Schema(description = "비밀번호", example = "Password1!")
            @NotBlank String password
    ) {
    }

    public record TermsAgreement(
            @Schema(description = "만 14세 이상 동의 여부", example = "true")
            @NotNull Boolean ageOver14Agreed,

            @Schema(description = "서비스 이용약관 필수 동의 여부", example = "true")
            @NotNull Boolean serviceTermsAgreed,

            @Schema(description = "개인정보 처리방침 필수 동의 여부", example = "true")
            @NotNull Boolean privacyPolicyAgreed,

            @Schema(description = "위치정보 제공 선택 동의 여부", example = "false")
            @NotNull Boolean locationAgreed,

            @Schema(description = "마케팅 수신 선택 동의 여부", example = "false")
            @NotNull Boolean marketingAgreed
    ) {
    }
}
