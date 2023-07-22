package com.sparta.community.service;

import com.sparta.community.dto.*;
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


    // 프로필 보기
    public ProfileResponseDto getProfile(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );
        return new ProfileResponseDto(user);
    }


    // 회원정보 수정 (username,한줄소개)
    @Transactional
    public void updateProfile(ProfileRequestDto requestDto, User user) {
        User userItem = userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
        );

        userItem.setUsername(requestDto.getUsername());
        userItem.setOneLiner(requestDto.getOneLiner());

    }

}
