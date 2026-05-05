package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.request.ChallengeMissionRequest;
import com.example.umc10th.domain.mission.dto.request.MissionRequest;
import com.example.umc10th.domain.mission.dto.response.MissionResponse;
import com.example.umc10th.domain.mission.dto.response.UserMissionPageResponse;
import com.example.umc10th.domain.mission.dto.response.UserMissionResponse;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.UserMission;
import com.example.umc10th.domain.mission.enums.MissionStatus;
import com.example.umc10th.domain.mission.exception.MissionException;
import com.example.umc10th.domain.mission.repository.MissionRepository;
import com.example.umc10th.domain.mission.repository.UserMissionRepository;
import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.domain.store.exception.StoreException;
import com.example.umc10th.domain.store.repository.StoreRepository;
import com.example.umc10th.domain.user.entity.User;
import com.example.umc10th.domain.user.exception.UserException;
import com.example.umc10th.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionServiceImpl implements MissionService {

    private final MissionRepository missionRepository;
    private final UserMissionRepository userMissionRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public MissionResponse addMission(MissionRequest request) {
        Store store = storeRepository.findById(request.storeId())
                .orElseThrow(() -> new StoreException("존재하지 않는 가게입니다."));

        Mission mission = MissionConverter.toMission(request, store);
        Mission saved = missionRepository.save(mission);
        return MissionConverter.toMissionResponse(saved);
    }

    @Override
    @Transactional
    public UserMissionResponse challengeMission(ChallengeMissionRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new UserException("존재하지 않는 유저입니다."));

        Mission mission = missionRepository.findById(request.missionId())
                .orElseThrow(() -> new MissionException("존재하지 않는 미션입니다."));

        if (userMissionRepository.existsByUserIdAndMissionId(user.getId(), mission.getId())) {
            throw new MissionException("이미 도전 중이거나 완료한 미션입니다.");
        }

        UserMission userMission = MissionConverter.toUserMission(user, mission);
        UserMission saved = userMissionRepository.save(userMission);
        return MissionConverter.toUserMissionResponse(saved);
    }

    @Override
    public UserMissionPageResponse getChallengingMissions(Long userId, int page, int size) {
        if (!userRepository.existsById(userId)) {
            throw new UserException("존재하지 않는 유저입니다.");
        }

        Page<UserMission> result = userMissionRepository.findByUserIdAndStatus(
                userId,
                MissionStatus.CHALLENGING,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        );
        return MissionConverter.toUserMissionPageResponse(result);
    }

    @Override
    @Transactional
    public UserMissionResponse completeMission(Long userMissionId) {
        UserMission userMission = userMissionRepository.findById(userMissionId)
                .orElseThrow(() -> new MissionException("존재하지 않는 도전 미션입니다."));

        if (userMission.getStatus() == MissionStatus.COMPLETED) {
            throw new MissionException("이미 완료된 미션입니다.");
        }

        userMission.complete();
        return MissionConverter.toUserMissionResponse(userMission);
    }
}
