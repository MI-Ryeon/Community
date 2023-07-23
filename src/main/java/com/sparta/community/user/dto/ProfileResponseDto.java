package com.sparta.community.user.dto;

import com.sparta.community.user.entity.User;
import lombok.Getter;

@Getter
public class ProfileResponseDto {
    private String nickname;
    private String oneLiner;

    public ProfileResponseDto(User user) {
        this.nickname = user.getNickname();
        this.oneLiner = user.getOneLiner();
    }
}
