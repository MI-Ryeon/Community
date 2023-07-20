package com.sparta.community.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoDto {
    String username;
    String email;
    String oneLiner;
    String ImgUrl;

    public UserInfoDto(String username) {
        this.username = username;
    }
}