package com.example.umc10th.domain.member.dto;

import com.example.umc10th.domain.member.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class MemberReqDto {

    public record Create(
            @NotBlank @Size(max = 20) String name,
            @NotNull Gender gender,
            @NotNull LocalDate birthDate,
            @NotBlank @Size(max = 255) String address,
            @NotBlank @Size(max = 20) String nickname
    ) {
    }
}