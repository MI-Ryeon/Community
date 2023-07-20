package com.sparta.community.entity;

import com.sparta.community.dto.SignupRequestDto;
import com.sparta.community.dto.UserRequestDto;
import com.sparta.community.dto.UserUpdateDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Profile;

// 사용자 정보를 담은 엔티티

@Entity
@Getter
@Setter
@Table(name = "user")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 로그인 시 사용 (ayboori)
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String email;
    // 한줄 소개
    @Column
    private String oneLiner;
    // 이미지
    @Column
    private String ImgUrl;


    public User(SignupRequestDto signupRequestDto, String password){
        this.username = signupRequestDto.getUsername();
        this.password = password;

        this.email = signupRequestDto.getEmail();
        this.oneLiner = "자기소개를 입력해주세요.";
        this.ImgUrl = "";
    }

    // User 받기?
    public User(UserRequestDto userRequestDto){
        this.username = userRequestDto.getUsername();

        this.email = userRequestDto.getEmail();

        this.oneLiner = "자기소개를 입력해주세요.";

        this.ImgUrl = "";
    }


    // 회원정보 수정
    public void update(UserRequestDto requestDto) {
        // id를 어떻게 해야하나
        this.username = requestDto.getUsername();
        this.email = requestDto.getEmail(); // 이메일 수정...??
        this.oneLiner = requestDto.getOneLiner();
        this.ImgUrl = requestDto.getImgUrl();

    }

    public User(UserUpdateDto userupdateDto) {
        this.username = userupdateDto.getUsername();
        this.oneLiner = userupdateDto.getOneLiner();
        this.ImgUrl = userupdateDto.getImgUrl();
    }



}

