package com.sparta.community.admin.service;

import com.sparta.community.admin.dto.AdminUserListResponseDto;
import com.sparta.community.admin.dto.AdminUserResponseDto;
import com.sparta.community.admin.dto.AdminUserRoleRequestDto;
import com.sparta.community.post.entity.Post;
import com.sparta.community.post.repository.PostRepository;
import com.sparta.community.user.dto.UserRequestDto;
import com.sparta.community.user.entity.User;
import com.sparta.community.user.entity.UserRoleEnum;
import com.sparta.community.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // 회원 전체 조회 > 리스트
    public AdminUserListResponseDto getUsers() {
        List<AdminUserResponseDto> userList = userRepository.findAll().stream()
                .map(AdminUserResponseDto::new).collect(Collectors.toList());
        return new AdminUserListResponseDto(userList);
    }

    // 회원 개별 조회
    public AdminUserResponseDto getUserById(Long id) {
        User user = findUser(id);

        return new AdminUserResponseDto(user);
    }

    // 회원 정보 수정 -> 기존 프로필 수정과 합쳐질 예정
    public void updateUserProfile(Long id, UserRequestDto requestDto) {
    }

    // 회원 권한 변경
    @Transactional
    public void updateUserRole(Long id, AdminUserRoleRequestDto requestDto) {
        User user = findUser(id);

        if (isAdmin(user)) {
            throw new IllegalArgumentException("관리자는 수정할 수 없습니다.");
        }

        user.setRole(requestDto.getRole());
    }

    // 탈퇴 시킬 회원의 게시글 삭제 -> 회원 탈퇴
    public void deleteUser(Long id) {
        User user = findUser(id);

        if (isAdmin(user)) {
            throw new IllegalArgumentException("관리자는 탈퇴할 수 없습니다.");
        }

        List<Post> postList = postRepository.findAllByUser(user);
        postRepository.deleteAll(postList);
        userRepository.delete(findUser(id));
    }

    // 회원 차단 구현 예정
    public void blockUser(Long id) {
    }

    // id 값으로 회원을 조회하는 메소드
    public User findUser(long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 회원은 존재하지 않습니다.")
        );
    }

    // 회원 권한 확인 ADMIN 이면 true, USER 이면 false;
    private boolean isAdmin(User user) {
        UserRoleEnum userRoleEnum = user.getRole();
        return userRoleEnum == UserRoleEnum.ADMIN;
    }
}
