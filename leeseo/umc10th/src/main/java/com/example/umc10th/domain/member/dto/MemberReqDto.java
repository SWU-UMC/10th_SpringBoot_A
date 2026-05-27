package com.example.umc10th.domain.member.dto;

import com.example.umc10th.domain.member.enums.FoodType;
import com.example.umc10th.domain.member.enums.Gender;
import com.example.umc10th.domain.mission.enums.Address;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class MemberReqDto {

    public record TermList (
        List<Term> terms
    ) {}

    @Schema(name = "Term", description = "약관별 동의 여부를 저장합니다.")
    public record Term (
        Long termId,
        Boolean agreed
    ) {}

    @Schema(name = "Profile", description = "수정할 프로필 정보를 저장합합니다.")
    public record Profile (
            @NotNull(message = "사용자 ID는 필수입니다.")
            Long id,
            String nickname,
            Gender gender,
            LocalDate birth,
            Address address,
            String fullAddress,
            List<FoodType> food
    ) {}

    @Schema(name = "Nickname", description = "수정할 닉네임을 저장합합니다.")
    public record Nickname (
            @NotNull(message = "사용자 ID는 필수입니다.")
            Long id,
            @NotBlank(message = "닉네임 입력은 필수입니다.")
            String nickname
    ) {}
}
