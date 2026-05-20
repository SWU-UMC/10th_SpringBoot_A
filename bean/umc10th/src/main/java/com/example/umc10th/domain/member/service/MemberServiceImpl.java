package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.converter.MemberConverter;
import com.example.umc10th.domain.member.dto.MemberReqDto;
import com.example.umc10th.domain.member.dto.MemberResDto;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.entity.MemberPrefer;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberPreferRepository;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.enums.MemberMissionStatus;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
import com.example.umc10th.domain.store.entity.FoodCategory;
import com.example.umc10th.domain.store.repository.FoodCategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final MemberPreferRepository memberPreferRepository;
    private final FoodCategoryRepository foodCategoryRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberResDto.MemberInfo getMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        return MemberConverter.toMemberInfo(member);
    }

    @Override
    @Transactional
    public MemberResDto.MemberInfo createMember(MemberReqDto.Create request) {
        Member savedMember = memberRepository.save(MemberConverter.toEntity(request));
        return MemberConverter.toMemberInfo(savedMember);
    }

    @Override
    public MemberResDto.GetMyPageResponse getMyPage(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Integer totalPoint = memberMissionRepository.sumRewardPointByMemberIdAndStatus(
                memberId,
                MemberMissionStatus.COMPLETE
        );

        return MemberConverter.toMyPageResponse(member, totalPoint);
    }

    @Override
    @Transactional
    public MemberResDto.SignUpResponse signUp(MemberReqDto.SignUpRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new MemberException(MemberErrorCode.EMAIL_ALREADY_EXISTS);
        }

        List<Long> distinctCategoryIds = request.preferCategoryIds().stream()
                .distinct()
                .toList();

        List<FoodCategory> categories = foodCategoryRepository.findAllById(distinctCategoryIds);
        if (categories.size() != distinctCategoryIds.size()) {
            throw new MemberException(MemberErrorCode.FOOD_CATEGORY_NOT_FOUND);
        }

        String encodedPassword = passwordEncoder.encode(request.password());
        Member member = memberRepository.save(MemberConverter.toEntity(request, encodedPassword));

        List<MemberPrefer> memberPrefers = categories.stream()
                .map(category -> MemberPrefer.builder()
                        .member(member)
                        .category(category)
                        .build())
                .toList();
        memberPreferRepository.saveAll(memberPrefers);

        return MemberConverter.toSignUpResponse(member, categories);
    }
}
