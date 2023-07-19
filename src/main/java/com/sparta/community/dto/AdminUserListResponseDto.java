package com.sparta.community.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class AdminUserListResponseDto {
    private final List<AdminUserResponseDto> usersList;

    public AdminUserListResponseDto(List<AdminUserResponseDto> userList) {
        this.usersList = userList;
    }
}
