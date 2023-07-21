package com.sparta.community.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDto {
    // API result 반환을 위한 DTO
    // 성공 MSG와 statues code(상태 코드)를 반환
    private String message;
    private int statusCode;

    @Builder
    public ApiResponseDto(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
