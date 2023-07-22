package com.sparta.community.common.advice;

import com.sparta.community.common.dto.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalControllerAdvice {

    // SignupRequestDto 의 Pattern Annotation 예외 처리
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiResponseDto> handleException(MethodArgumentNotValidException ex) {
        ApiResponseDto apiResponseDto = new ApiResponseDto(Objects.requireNonNull(ex.getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.BAD_REQUEST);
    }

    // 프로젝트 전반적인 IllegalArgumentException 의 예외 처리(포스트맨에서 실습할 때 활성화)
//    @ExceptionHandler({IllegalArgumentException.class})
//    public ResponseEntity<ApiResponseDto> handleException(IllegalArgumentException ex) {
//        ApiResponseDto apiResponseDto = new ApiResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
//        return new ResponseEntity<>(apiResponseDto, HttpStatus.BAD_REQUEST);
//    }

    // USER 가 ADMIN PAGE 에 접속하려 할 때 권한 없음을 알려주는 예외 처리
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ApiResponseDto> handleException(AccessDeniedException ex) {
        ApiResponseDto apiResponseDto = new ApiResponseDto(ex.getMessage(), HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.FORBIDDEN);
    }
}
