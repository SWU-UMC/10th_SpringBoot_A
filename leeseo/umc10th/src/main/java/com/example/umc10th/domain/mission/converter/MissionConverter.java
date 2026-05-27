package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.member.dto.MemberResDto;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.dto.MissionResDto;
import com.example.umc10th.domain.mission.dto.OwnerNumber;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;

import java.util.List;

public class MissionConverter {
    public static MemberMission toMemberMission(
            Member member,
            Mission mission
    ) {
        return MemberMission.builder()
                .member(member)
                .mission(mission)
                .build();
    }
    
    public static OwnerNumber toOwnerNumber (
            MemberMission mission
    ) {
        return OwnerNumber.builder()
                .ownerNumber(mission.getSuccessNumber())
                .build();
    }

    public static <T> MissionResDto.Pagination<T> toPagination(
            List<T> data,
            Integer pageNumber,
            Integer pageSize
    ) {
        return MissionResDto.Pagination.<T>builder()
                .data(data)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .build();
    }
}
