package com.sparta.community.admin.dto;

import com.sparta.community.admin.dto.AdminUserResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class AdminUserListResponseDto {
    private final List<AdminUserResponseDto> usersList;

    public AdminUserListResponseDto(List<AdminUserResponseDto> userList) {
        this.usersList = userList;
    }
}
