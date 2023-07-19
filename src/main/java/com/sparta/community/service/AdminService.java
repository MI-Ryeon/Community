package com.sparta.community.service;

import com.sparta.community.dto.AdminUserListResponseDto;
import com.sparta.community.dto.AdminUserResponseDto;
import com.sparta.community.entity.User;
import com.sparta.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

    public AdminUserListResponseDto getUsers() {
        List<AdminUserResponseDto> userList = userRepository.findAll().stream()
                .map(AdminUserResponseDto::new).collect(Collectors.toList());
        return new AdminUserListResponseDto(userList);
    }

    public AdminUserResponseDto getUserById(Long id) {
        User user = findUser(id);

        return new AdminUserResponseDto(user);
    }

    public User findUser(long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 회원은 존재하지 않습니다.")
        );
    }
}
