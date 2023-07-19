package com.sparta.community.dto;

import com.sparta.community.entity.User;
import com.sparta.community.entity.UserRoleEnum;
import lombok.Getter;

@Getter
public class AdminUserResponseDto {
    private Long id;
    private String username;
    private UserRoleEnum role;

    public AdminUserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole();
    }
}
