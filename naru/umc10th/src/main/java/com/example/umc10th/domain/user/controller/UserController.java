package com.example.umc10th.domain.user.controller;

import com.example.umc10th.domain.user.dto.UserRequestDto;
import com.example.umc10th.domain.user.dto.UserResponseDto;
import com.example.umc10th.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    // 아무것도 받지 않은 경우
    @GetMapping("/test")
    public String test() {
        return "test";
    }

    // Query Parameter
    @PostMapping("/query-parameter")
    public String queryParameter(
            @RequestParam String queryParameter
    ) {
        return userService.singleParameter(queryParameter);
    }

    // Request Body
    @PostMapping("/request-body")
    public UserResponseDto.RequestBody requestBody(
            @RequestBody UserRequestDto.RequestBody dto
    ) {
        return userService.requestBody(dto);
    }

    // Path Variable
    @PostMapping("/{pathVariable}")
    public String pathVariable(
            @PathVariable String pathVariable
    ) {
        return userService.singleParameter(pathVariable);
    }

    // Header
    @PostMapping("/header")
    public String header(
            @RequestHeader("test") String test
    ) {
        return userService.singleParameter(test);
    }
}
