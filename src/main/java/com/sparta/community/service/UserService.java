package com.sparta.community.service;

import com.sparta.community.dto.SignupRequestDto;
import com.sparta.community.entity.User;
import com.sparta.community.entity.UserRoleEnum;
import com.sparta.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public void signup(SignupRequestDto requestDto) {
        // pw 변환
        String password = passwordEncoder.encode(requestDto.getPassword());

        // username 중복 확인
        checkUsername(requestDto.getUsername());

        // email 중복 확인
        checkEmail(requestDto.getEmail());

        // role 부여
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀렸습니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 DB에 등록
        userRepository.save(new User(requestDto, password, role));
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
}
