package com.sparta.community.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String imgUrl;

    @Column
    private String nickname;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;


//    public User(SignupRequestDto signupRequestDto, String password, UserRoleEnum role) {
//        this.username = signupRequestDto.getUsername();
//        this.password = password;
//        this.email = signupRequestDto.getEmail();
//        this.oneLiner = signupRequestDto.getOneLiner();
//        this.role = role;
//        this.imgUrl = "";
//    }

    // User 받기?
//    public User(UserRequestDto userRequestDto) {
//        this.username = userRequestDto.getUsername();
//        this.email = userRequestDto.getEmail();
//        this.oneLiner = userRequestDto.getOneLiner();
//        this.imgUrl = "";
//    }

    public User(String username, String password, String email, String oneLiner, String nickname, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.oneLiner = oneLiner;
        this.nickname = nickname;
        this.role = role;
    }

    // 회원정보 수정
//    public void update(ProfileRequestDto requestDto) {
//        this.username = requestDto.getUsername();
//        this.oneLiner = requestDto.getOneLiner();
//    }

//    public User(UserUpdateDto userupdateDto) {
//        this.username = userupdateDto.getUsername();
//        this.oneLiner = userupdateDto.getOneLiner();
//        this.imgUrl = userupdateDto.getImgUrl();
//    }


}

