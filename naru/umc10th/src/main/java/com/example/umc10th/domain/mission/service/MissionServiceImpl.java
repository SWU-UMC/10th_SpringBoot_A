package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.MissionResponseDto;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.enums.MissionStatus;
import com.example.umc10th.domain.mission.entity.mapping.UserMission;
import com.example.umc10th.domain.mission.repository.MissionRepository;
import com.example.umc10th.domain.mission.repository.UserMissionRepository;
import com.example.umc10th.domain.region.entity.RegionProgress;
import com.example.umc10th.domain.region.repository.RegionProgressRepository;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import com.example.umc10th.domain.store.entity.Region;
import com.example.umc10th.domain.store.repository.RegionRepository;
import com.example.umc10th.domain.user.exception.UserException;
import com.example.umc10th.domain.user.exception.code.UserErrorCode;
import com.example.umc10th.domain.user.repository.UserRepository;
import com.example.umc10th.global.common.dto.CursorPageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionServiceImpl implements MissionService {

    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int MAX_PAGE_SIZE = 50;

    private final UserMissionRepository userMissionRepository;
    private final MissionRepository missionRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final RegionRepository regionRepository;
    private final RegionProgressRepository regionProgressRepository;

    @Override
    public CursorPageResponseDto<MissionResponseDto.MissionPreviewDto> getMyMissions(
            Long userId,
            MissionStatus status,
            Long regionId,
            Long cursor,
            int size
    ) {
        validateUser(userId);

        int pageSize = normalizeSize(size);
        Slice<UserMission> userMissions = userMissionRepository.findMyMissionsByCursor(
                userId,
                status,
                regionId,
                cursor,
                PageRequest.of(0, pageSize)
        );

        Slice<MissionResponseDto.MissionPreviewDto> result = userMissions.map(userMission ->
                MissionConverter.toMissionPreviewDto(userMission, canWriteReview(userMission))
        );

        return CursorPageResponseDto.of(result, MissionResponseDto.MissionPreviewDto::userMissionId);
    }

    @Override
    public CursorPageResponseDto<MissionResponseDto.AvailableMissionDto> getAvailableMissions(
            Long userId,
            Long regionId,
            Long cursor,
            int size
    ) {
        validateUser(userId);

        int pageSize = normalizeSize(size);
        Slice<Mission> missions = missionRepository.findAvailableMissionsByCursor(
                userId,
                regionId,
                cursor,
                PageRequest.of(0, pageSize)
        );

        Slice<MissionResponseDto.AvailableMissionDto> result =
                missions.map(MissionConverter::toAvailableMissionDto);

        return CursorPageResponseDto.of(result, MissionResponseDto.AvailableMissionDto::missionId);
    }

    @Override
    public MissionResponseDto.MissionSummaryResultDto getRegionProgress(
            Long userId,
            Long regionId
    ) {
        validateUser(userId);

        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new IllegalArgumentException("Region not found"));

        RegionProgress regionProgress = regionProgressRepository.findLatestByUserIdAndRegionId(
                        userId,
                        regionId,
                        PageRequest.of(0, 1)
                )
                .getContent()
                .stream()
                .findFirst()
                .orElse(null);

        return MissionConverter.toMissionSummaryResultDto(region, regionProgress);
    }

    private void validateUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserException(UserErrorCode.USER_NOT_FOUND);
        }
    }

    private int normalizeSize(int size) {
        if (size <= 0) {
            return DEFAULT_PAGE_SIZE;
        }

        return Math.min(size, MAX_PAGE_SIZE);
    }

    private boolean canWriteReview(UserMission userMission) {
        return userMission.getStatus() == MissionStatus.SUCCESS
                && !reviewRepository.existsByUserMissionId(userMission.getId());
    }
}
