package com.example.umc10th.domain.auth.service;

import com.example.umc10th.domain.auth.converter.AuthConverter;
import com.example.umc10th.domain.auth.dto.AuthRequestDto;
import com.example.umc10th.domain.auth.dto.AuthResponseDto;
import com.example.umc10th.domain.auth.exception.AuthException;
import com.example.umc10th.domain.auth.exception.code.AuthErrorCode;
import com.example.umc10th.domain.category.entity.FoodCategory;
import com.example.umc10th.domain.category.entity.mapping.UserFoodCategory;
import com.example.umc10th.domain.category.repository.FoodCategoryRepository;
import com.example.umc10th.domain.category.repository.UserFoodCategoryRepository;
import com.example.umc10th.domain.term.entity.Term;
import com.example.umc10th.domain.term.entity.mapping.UserAgreement;
import com.example.umc10th.domain.term.repository.TermRepository;
import com.example.umc10th.domain.term.repository.UserAgreementRepository;
import com.example.umc10th.domain.user.entity.User;
import com.example.umc10th.domain.user.exception.UserException;
import com.example.umc10th.domain.user.exception.code.UserErrorCode;
import com.example.umc10th.domain.user.repository.UserRepository;
import com.example.umc10th.global.security.jwt.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final FoodCategoryRepository foodCategoryRepository;
    private final UserFoodCategoryRepository userFoodCategoryRepository;
    private final TermRepository termRepository;
    private final UserAgreementRepository userAgreementRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public AuthResponseDto.SignUpResultDto signUp(AuthRequestDto.SignUpDto request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new UserException(UserErrorCode.DUPLICATE_EMAIL);
        }

        Map<Long, Boolean> agreementMap = request.agreements().stream()
                .collect(Collectors.toMap(
                        AuthRequestDto.AgreementDto::termId,
                        AuthRequestDto.AgreementDto::isAgreed,
                        (first, second) -> second
                ));

        validateRequiredTerms(agreementMap);
        List<Term> requestedTerms = getRequestedTerms(agreementMap.keySet());
        List<FoodCategory> preferredFoodCategories = getPreferredFoodCategories(request.preferredFoodCategoryIds());

        User user = AuthConverter.toUser(request, passwordEncoder.encode(request.password()));
        User savedUser = userRepository.save(user);

        List<UserFoodCategory> userFoodCategories = preferredFoodCategories.stream()
                .map(foodCategory -> UserFoodCategory.builder()
                        .user(savedUser)
                        .foodCategory(foodCategory)
                        .build())
                .toList();

        userFoodCategoryRepository.saveAll(userFoodCategories);
        saveUserAgreements(savedUser, requestedTerms, agreementMap);
        return AuthConverter.toSignUpResultDto(savedUser);
    }

    @Override
    public AuthResponseDto.LoginResultDto login(AuthRequestDto.LoginDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return AuthResponseDto.LoginResultDto.builder()
                .grantType("Bearer")
                .accessToken(jwtUtil.createAccessToken(userDetails))
                .refreshToken(jwtUtil.createRefreshToken(userDetails))
                .accessTokenExpiresIn(jwtUtil.getAccessTokenExpiration())
                .build();
    }

    private void validateRequiredTerms(Map<Long, Boolean> agreementMap) {
        List<Term> requiredTerms = termRepository.findAllByIsRequiredTrue();

        boolean hasNotAgreedRequiredTerm = requiredTerms.stream()
                .anyMatch(term -> !Boolean.TRUE.equals(agreementMap.get(term.getId())));

        if (hasNotAgreedRequiredTerm) {
            throw new AuthException(AuthErrorCode.REQUIRED_TERM_NOT_AGREED);
        }
    }

    private List<Term> getRequestedTerms(Set<Long> termIds) {
        List<Term> terms = termRepository.findAllById(termIds);

        if (terms.size() != termIds.size()) {
            throw new AuthException(AuthErrorCode.TERM_NOT_FOUND);
        }

        return terms;
    }

    private List<FoodCategory> getPreferredFoodCategories(List<Long> foodCategoryIds) {
        Set<Long> uniqueFoodCategoryIds = Set.copyOf(foodCategoryIds);
        List<FoodCategory> foodCategories = foodCategoryRepository.findAllById(uniqueFoodCategoryIds);

        if (foodCategories.size() != uniqueFoodCategoryIds.size()) {
            throw new AuthException(AuthErrorCode.FOOD_CATEGORY_NOT_FOUND);
        }

        return foodCategories;
    }

    private void saveUserAgreements(User user, List<Term> terms, Map<Long, Boolean> agreementMap) {
        List<UserAgreement> userAgreements = terms.stream()
                .map(term -> UserAgreement.builder()
                        .user(user)
                        .term(term)
                        .isAgreed(agreementMap.get(term.getId()))
                        .build())
                .toList();

        userAgreementRepository.saveAll(userAgreements);
    }
}
