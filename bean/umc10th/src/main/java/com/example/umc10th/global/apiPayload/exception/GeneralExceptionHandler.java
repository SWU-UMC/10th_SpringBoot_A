package com.example.umc10th.global.apiPayload.exception;

import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.dto.ReasonDto;
import com.example.umc10th.global.apiPayload.code.status.ErrorStatus;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(GeneralException exception) {
        ReasonDto reason = exception.getErrorReasonHttpStatus();
        return ResponseEntity
                .status(reason.httpStatus())
                .body(ApiResponse.onFailure(reason.code(), reason.message(), null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new LinkedHashMap<>();

        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ErrorStatus status = ErrorStatus.VALIDATION_ERROR;
        return ResponseEntity
                .status(status.getHttpStatus())
                .body(ApiResponse.onFailure(status.getCode(), status.getMessage(), errors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception exception) {
        ErrorStatus status = ErrorStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity
                .status(status.getHttpStatus())
                .body(ApiResponse.onFailure(status.getCode(), status.getMessage(), exception.getMessage()));
    }
}