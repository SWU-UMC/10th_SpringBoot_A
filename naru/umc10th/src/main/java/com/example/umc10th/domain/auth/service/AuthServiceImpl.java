package com.example.umc10th.domain.auth.service;

import com.example.umc10th.domain.auth.converter.AuthConverter;
import com.example.umc10th.domain.auth.dto.AuthRequestDto;
import com.example.umc10th.domain.auth.dto.AuthResponseDto;
import com.example.umc10th.domain.category.entity.FoodCategory;
import com.example.umc10th.domain.category.entity.mapping.UserFoodCategory;
import com.example.umc10th.domain.category.repository.FoodCategoryRepository;
import com.example.umc10th.domain.category.repository.UserFoodCategoryRepository;
import com.example.umc10th.domain.user.entity.User;
import com.example.umc10th.domain.user.exception.UserException;
import com.example.umc10th.domain.user.exception.code.UserErrorCode;
import com.example.umc10th.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final FoodCategoryRepository foodCategoryRepository;
    private final UserFoodCategoryRepository userFoodCategoryRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public AuthResponseDto.SignUpResultDto signUp(AuthRequestDto.SignUpDto request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new UserException(UserErrorCode.DUPLICATE_EMAIL);
        }

        User user = AuthConverter.toUser(request, passwordEncoder.encode(request.password()));
        User savedUser = userRepository.save(user);

        List<UserFoodCategory> userFoodCategories = request.preferredFoods().stream()
                .distinct()
                .map(foodName -> {
                    FoodCategory foodCategory = foodCategoryRepository.findByName(foodName)
                            .orElseGet(() -> foodCategoryRepository.save(
                                    FoodCategory.builder()
                                            .name(foodName)
                                            .build()
                            ));
                    return UserFoodCategory.builder()
                            .user(savedUser)
                            .foodCategory(foodCategory)
                            .build();
                })
                .toList();

        userFoodCategoryRepository.saveAll(userFoodCategories);
        return AuthConverter.toSignUpResultDto(savedUser);
    }
}
