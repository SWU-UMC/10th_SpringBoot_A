package com.example.umc10th.domain.home.service;

import com.example.umc10th.domain.home.converter.HomeConverter;
import com.example.umc10th.domain.home.dto.response.HomeResponse;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.repository.MissionRepository;
import com.example.umc10th.domain.mission.repository.UserMissionRepository;
import com.example.umc10th.domain.store.entity.Region;
import com.example.umc10th.domain.store.exception.StoreException;
import com.example.umc10th.domain.store.exception.code.StoreErrorCode;
import com.example.umc10th.domain.store.repository.RegionRepository;
import com.example.umc10th.domain.user.entity.User;
import com.example.umc10th.domain.user.exception.UserException;
import com.example.umc10th.domain.user.exception.code.UserErrorCode;
import com.example.umc10th.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeServiceImpl implements HomeService {

    private final UserRepository userRepository;
    private final RegionRepository regionRepository;
    private final MissionRepository missionRepository;
    private final UserMissionRepository userMissionRepository;

    @Override
    public HomeResponse getHome(Long userId, Long regionId, int page, int size) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new StoreException(StoreErrorCode.REGION_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        // 유저가 이미 도전중/완료한 미션은 도전 가능 목록에서 제외
        List<Long> excluded = userMissionRepository.findMissionIdsByUserId(userId);
        Page<Mission> missions = excluded.isEmpty()
                ? missionRepository.findByStore_Region_Id(regionId, pageable)
                : missionRepository.findByStore_Region_IdAndIdNotIn(regionId, excluded, pageable);

        return HomeConverter.toHomeResponse(user, region, missions);
    }
}
