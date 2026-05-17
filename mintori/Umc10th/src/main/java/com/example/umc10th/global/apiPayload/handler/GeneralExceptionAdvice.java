package com.example.umc10th.global.apiPayload.handler;

import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import com.example.umc10th.global.apiPayload.code.GeneralErrorCode;
import com.example.umc10th.global.apiPayload.exception.BaseException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GeneralExceptionAdvice {

    // 프로젝트에서 발생한 예외 처리
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<Void>> handleBaseException(
            BaseException e
    ) {
        BaseErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.onFailure(errorCode, null));
    }

    /**
     * [필수 3] @Valid 검증 실패 (Request Body) 처리
     *  - 필드별 메시지를 result 로 함께 내려준다.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e
    ) {
        Map<String, String> errors = new LinkedHashMap<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            String message = fieldError.getDefaultMessage() != null
                    ? fieldError.getDefaultMessage()
                    : "유효하지 않은 값입니다.";
            errors.merge(fieldError.getField(), message,
                    (existing, incoming) -> existing + "; " + incoming);
        }

        BaseErrorCode errorCode = GeneralErrorCode.VALIDATION_FAILED;
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.onFailure(errorCode, errors));
    }

    /**
     * [필수 3] @Validated 가 붙은 파라미터/PathVariable 검증 실패 처리
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleConstraintViolation(
            ConstraintViolationException e
    ) {
        Map<String, String> errors = new LinkedHashMap<>();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            String path = violation.getPropertyPath().toString();
            errors.put(path, violation.getMessage());
        }

        BaseErrorCode errorCode = GeneralErrorCode.VALIDATION_FAILED;
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.onFailure(errorCode, errors));
    }

    /**
     * 요청 Body 가 비어있거나 JSON 파싱 실패 시
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<String>> handleHttpMessageNotReadable(
            HttpMessageNotReadableException e
    ) {
        BaseErrorCode errorCode = GeneralErrorCode.HTTP_MESSAGE_NOT_READABLE;
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.onFailure(errorCode, e.getMostSpecificCause().getMessage()));
    }

    /**
     * 쿼리 파라미터 / PathVariable 타입 변환 실패
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<String>> handleTypeMismatch(
            MethodArgumentTypeMismatchException e
    ) {
        BaseErrorCode errorCode = GeneralErrorCode.METHOD_ARGUMENT_TYPE_MISMATCH;
        String detail = String.format(
                "파라미터 '%s' 의 값 '%s' 을(를) 변환할 수 없습니다.",
                e.getName(), String.valueOf(e.getValue())
        );
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.onFailure(errorCode, detail));
    }

    // 그 외의 정의되지 않은 모든 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(
            Exception e
    ) {
        BaseErrorCode errorCode = GeneralErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.onFailure(
                        errorCode,
                        e.getMessage()));
    }
}
