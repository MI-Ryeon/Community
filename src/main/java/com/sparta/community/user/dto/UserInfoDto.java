package com.sparta.community.user.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoDto {
    private String username;
    private String email;
    private String oneLiner;
    private String imgUrl;
}