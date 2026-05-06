package com.example.umc10th.domain.user.service;

import com.example.umc10th.domain.user.converter.UserConverter;
import com.example.umc10th.domain.user.dto.UserRequestDto;
import com.example.umc10th.domain.user.dto.UserResponseDto;
import com.example.umc10th.domain.user.entity.User;
import com.example.umc10th.domain.user.exception.UserException;
import com.example.umc10th.domain.user.exception.code.UserErrorCode;
import com.example.umc10th.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public String singleParameter(String singleParameter) {
        return singleParameter;
    }

    @Override
    public UserResponseDto.RequestBody requestBody(UserRequestDto.RequestBody dto) {
        return UserConverter.toRequestBody(dto.stringTest(), dto.longTest());
    }

    @Override
    public UserResponseDto.MyPageResultDto getMyPage(UserRequestDto.GetMyPageDto request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
        return UserConverter.toMyPageResultDto(user);
    }
}
