package com.sparta.community.service;

import com.sparta.community.dto.AuthRequestDto;
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
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public void signup(AuthRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        UserRoleEnum role = UserRoleEnum.USER;
        String email =requestDto.getEmail();

        idCheck(username);

        User user = new User(username, password, role,email);
        userRepository.save(user);
    }

    public void login(AuthRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        //사용자 확인 (username 이 없는 경우)
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        //비밀번호 확인 (password 가 다른 경우)
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    public void idCheck(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }


    }
}