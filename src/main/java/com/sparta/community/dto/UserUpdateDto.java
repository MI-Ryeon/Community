package com.sparta.community.dto;

import lombok.Data;

import java.util.Optional;

@Data
public class UserUpdateDto {
    private String username;
    private String oneLiner;
    private String ImgUrl;

}
