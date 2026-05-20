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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
        saveUserAgreements(savedUser, requestedTerms, agreementMap);
        return AuthConverter.toSignUpResultDto(savedUser);
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
