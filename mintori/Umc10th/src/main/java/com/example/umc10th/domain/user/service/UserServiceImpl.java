package com.example.umc10th.domain.user.service;

import com.example.umc10th.domain.mission.enums.MissionStatus;
import com.example.umc10th.domain.mission.repository.UserMissionRepository;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import com.example.umc10th.domain.user.converter.UserConverter;
import com.example.umc10th.domain.user.dto.request.UserRequest;
import com.example.umc10th.domain.user.dto.response.MyPageResponse;
import com.example.umc10th.domain.user.dto.response.UserResponse;
import com.example.umc10th.domain.user.entity.User;
import com.example.umc10th.domain.user.exception.UserException;
import com.example.umc10th.domain.user.exception.code.UserErrorCode;
import com.example.umc10th.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMissionRepository userMissionRepository;
    private final ReviewRepository reviewRepository;

    @Override
    @Transactional
    public UserResponse signUp(UserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new UserException(UserErrorCode.DUPLICATE_EMAIL);
        }
        User user = UserConverter.toUser(request);
        User saved = userRepository.save(user);
        return UserConverter.toUserResponse(saved);
    }

    @Override
    public MyPageResponse getMyPage(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        long challengingCount = userMissionRepository.countByUserIdAndStatus(userId, MissionStatus.CHALLENGING);
        long completedCount = userMissionRepository.countByUserIdAndStatus(userId, MissionStatus.COMPLETED);
        long reviewCount = reviewRepository.countByUserId(userId);

        return UserConverter.toMyPageResponse(user, challengingCount, completedCount, reviewCount);
    }
}
