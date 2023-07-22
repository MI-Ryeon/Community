package com.sparta.community.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthcodeDto {
    private String email;
    private String authCode;
}
