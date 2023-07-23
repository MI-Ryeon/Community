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
    private String email;
    private String oneLiner;
    private String nickname;
    private String imgUrl;
    private UserRoleEnum role;

    public AdminUserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.oneLiner = user.getOneLiner();
        this.nickname = user.getNickname();
        this.imgUrl = user.getImgUrl();
        this.role = user.getRole();
    }
}
