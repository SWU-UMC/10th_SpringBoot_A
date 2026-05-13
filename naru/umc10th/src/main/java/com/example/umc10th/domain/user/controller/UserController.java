package com.example.umc10th.domain.user.controller;

import com.example.umc10th.domain.user.dto.UserRequestDto;
import com.example.umc10th.domain.user.dto.UserResponseDto;
import com.example.umc10th.domain.user.exception.UserException;
import com.example.umc10th.domain.user.exception.code.UserErrorCode;
import com.example.umc10th.domain.user.service.UserService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import com.example.umc10th.global.apiPayload.code.GeneralSuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "사용자 API", description = "사용자 관련 API")
public class UserController {

    private final UserService userService;

    // 마이페이지 조회
    @Operation(summary = "마이페이지 조회 API")
    @GetMapping("/mypage")
    public ApiResponse<UserResponseDto.MyPageResultDto> getMyPage(
            @RequestParam Long userId
    ) {
        UserResponseDto.MyPageResultDto result = userService.getMyPage(userId);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, result);
    }

    // 아무것도 받지 않은 경우
    @Operation(summary = "사용자 예외 처리 테스트 API")
    @GetMapping("/test")
    public String test(
    ) throws Exception {
        throw new UserException(UserErrorCode.USER_NOT_FOUND);
    }

    // Query Parameter
    @Operation(summary = "쿼리 파라미터 전달 테스트 API")
    @PostMapping("/query-parameter")
    public ApiResponse<String> queryParameter(
            @RequestParam String queryParameter
    ) {
        BaseSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code, userService.singleParameter(queryParameter));
    }

    // Request Body
    @Operation(summary = "요청 본문 전달 테스트 API")
    @PostMapping("/request-body")
    public UserResponseDto.RequestBody requestBody(
            @RequestBody UserRequestDto.RequestBody dto
    ) {
        return userService.requestBody(dto);
    }

    // Path Variable
    @Operation(summary = "경로 변수 전달 테스트 API")
    @PostMapping("/{pathVariable}")
    public String pathVariable(
            @PathVariable String pathVariable
    ) {
        return userService.singleParameter(pathVariable);
    }

    // Header
    @Operation(summary = "요청 헤더 전달 테스트 API")
    @PostMapping("/header")
    public String header(
            @RequestHeader("test") String test
    ) {
        return userService.singleParameter(test);
    }
}
