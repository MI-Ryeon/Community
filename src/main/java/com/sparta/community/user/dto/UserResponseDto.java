package com.sparta.community.user.dto;

import com.sparta.community.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// mypage에서 user 정보 출력을 위해 생성
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private String username; // 로그인 시 사용할 아이디
    private String email;
    private String oneLine;     // 한줄 소개

    public UserResponseDto(User user){
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.oneLine = user.getOneLine();
    }
}
