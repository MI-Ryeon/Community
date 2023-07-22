package com.sparta.community.admin.dto;

import com.sparta.community.user.entity.User;
import com.sparta.community.user.entity.UserRoleEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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