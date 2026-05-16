package com.example.umc10th.domain.auth.dto;

import com.example.umc10th.domain.member.dto.MemberReqDto;
import com.example.umc10th.domain.member.enums.FoodType;
import com.example.umc10th.domain.member.enums.Gender;
import com.example.umc10th.domain.mission.enums.Address;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class AuthReqDto {

    @Schema(name = "SignUp", description = "회원가입 정보를 저장합합니다.")
    public record Signup (
            @Email
            @NotBlank
            String email,
            @NotBlank
            String password,
            @NotBlank
            String nickname,
            @NotNull
            Gender gender,
            @NotNull
            LocalDate birth,
            @NotNull
            Address address,
            @NotBlank
            String fullAddress,
            @NotNull
            List<FoodType> food
    ) {}
}
