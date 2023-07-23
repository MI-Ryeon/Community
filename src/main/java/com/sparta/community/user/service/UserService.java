package com.sparta.community.user.service;

import com.sparta.community.user.dto.ProfileRequestDto;
import com.sparta.community.user.dto.ProfileResponseDto;
import com.sparta.community.user.repository.SignupAuthRepository;
import com.sparta.community.user.dto.SignupRequestDto;
import com.sparta.community.user.entity.User;
import com.sparta.community.user.entity.UserRoleEnum;
import com.sparta.community.user.repository.UserRepository;
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
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    private final SignupAuthRepository signupAuthRepository;

    public void signup(SignupRequestDto requestDto) {
        // pw 변환
        String password = passwordEncoder.encode(requestDto.getPassword());
        String username = requestDto.getUsername();
        String email = requestDto.getEmail();
        String oneLiner = requestDto.getOneLiner();

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
        signupAuthRepository.findByEmail(requestDto.getEmail()).orElseThrow(() -> new IllegalArgumentException("이메일 인증을 해주세요."));


        // 사용자 DB에 등록
        userRepository.save(new User(username, password, email, oneLiner, role));
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

        // 회원가입 이메일 인증번호 검증여부 확인
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
