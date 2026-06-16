package com.example.umc10th.domain.user.service;

import com.example.umc10th.domain.mission.enums.MissionStatus;
import com.example.umc10th.domain.mission.repository.UserMissionRepository;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import com.example.umc10th.domain.user.converter.UserConverter;
import com.example.umc10th.domain.user.dto.request.LoginRequest;
import com.example.umc10th.domain.user.dto.request.UserRequest;
import com.example.umc10th.domain.user.dto.response.MyPageResponse;
import com.example.umc10th.domain.user.dto.response.TokenResponse;
import com.example.umc10th.domain.user.dto.response.UserResponse;
import com.example.umc10th.domain.user.entity.User;
import com.example.umc10th.domain.user.exception.UserException;
import com.example.umc10th.domain.user.exception.code.UserErrorCode;
import com.example.umc10th.domain.user.repository.UserRepository;
import com.example.umc10th.global.jwt.JwtTokenProvider;
import com.example.umc10th.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMissionRepository userMissionRepository;
    private final ReviewRepository reviewRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public UserResponse signUp(UserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new UserException(UserErrorCode.DUPLICATE_EMAIL);
        }
        // BCrypt 로 비밀번호를 솔트와 함께 해시 처리
        String encodedPassword = passwordEncoder.encode(request.password());
        User user = UserConverter.toUser(request, encodedPassword);
        User saved = userRepository.save(user);
        return UserConverter.toUserResponse(saved);
    }

    @Override
    public TokenResponse login(LoginRequest request) {
        // AuthenticationManager 가 CustomUserDetailsService + PasswordEncoder 로 자격 증명을 검증
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );
        } catch (AuthenticationException e) {
            // 사용자 없음 / 비밀번호 불일치 등 모든 인증 실패를 동일하게 처리
            throw new UserException(UserErrorCode.LOGIN_FAILED);
        }

        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        String accessToken = jwtTokenProvider.createAccessToken(
                principal.getUserId(), principal.getUsername());

        return new TokenResponse(
                principal.getUserId(),
                "Bearer",
                accessToken,
                jwtTokenProvider.getAccessTokenValidityInSeconds()
        );
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
