package com.example.umc10th.domain.auth.dto;

import com.example.umc10th.domain.user.entity.enums.Gender;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public class AuthRequestDto {

    public record SignUpDto(
            @NotBlank(message = "이메일은 필수입니다.")
            @Email(message = "이메일 형식이 올바르지 않습니다.")
            String email,

            @NotBlank(message = "비밀번호는 필수입니다.")
            @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
            String password,

            @NotBlank(message = "이름은 필수입니다.")
            String name,

            @NotNull(message = "성별은 필수입니다.")
            Gender gender,

            @NotNull(message = "생년월일은 필수입니다.")
            LocalDate birth,

            @NotBlank(message = "주소는 필수입니다.")
            String address,

            @NotEmpty(message = "선호 음식 종류는 1개 이상 선택해야 합니다.")
            List<@NotBlank(message = "선호 음식 이름은 비어 있을 수 없습니다.") String> preferredFoods,

            @NotEmpty(message = "약관 동의 목록은 필수입니다.")
            List<@Valid AgreementDto> agreements
    ) {}

    public record AgreementDto(
            @NotNull(message = "약관 ID는 필수입니다.")
            Long termId,

            @NotNull(message = "약관 동의 여부는 필수입니다.")
            Boolean isAgreed
    ) {}
}
