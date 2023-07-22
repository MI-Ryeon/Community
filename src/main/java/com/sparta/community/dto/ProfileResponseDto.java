package com.sparta.community.dto;

import com.sparta.community.entity.User;
import lombok.Getter;

@Getter
public class ProfileResponseDto {
    private String username;
    private String oneLiner;

    public ProfileResponseDto(User user) {
        this.username = user.getUsername();
        this.oneLiner = user.getOneLiner();
    }
}
