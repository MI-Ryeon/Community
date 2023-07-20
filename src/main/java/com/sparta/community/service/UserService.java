package com.sparta.community.service;

import com.sparta.community.dto.SignupRequestDto;
import com.sparta.community.dto.UserInfoDto;
import com.sparta.community.dto.UserUpdateDto;
import com.sparta.community.entity.User;
import com.sparta.community.entity.UserRoleEnum;
import com.sparta.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequestDto requestDto) {
        // pw 변환
        String password = passwordEncoder.encode(requestDto.getPassword());

        String username = requestDto.getUsername();
        String email = requestDto.getEmail();
        UserRoleEnum role = UserRoleEnum.USER;

        // username 중복 확인
        checkUsername(requestDto.getUsername());

        // email 중복 확인
        checkEmail(requestDto.getEmail());

        User user = new User(username,password,email,role);
        userRepository.save(user);

    }

    // username 중복 확인
    public void checkUsername(String username) {
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("입력하신 ID는 이미 존재하는 ID 입니다.");
        }
    }
    // email 중복 확인
    public void checkEmail(String email) {
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("이미 회원가입 된 이메일 입니다.");
        }
    }


    // 회원정보 검색



    // 회원정보 수정
    @Transactional
    public void updateUser(UserUpdateDto userupdateDto) {
        User updateUser = new User(userupdateDto);

    }

}
