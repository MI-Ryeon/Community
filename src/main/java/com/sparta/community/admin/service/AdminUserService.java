package com.sparta.community.admin.service;

import com.sparta.community.admin.dto.AdminUserListResponseDto;
import com.sparta.community.admin.dto.AdminUserResponseDto;
import com.sparta.community.admin.dto.AdminUserRoleRequestDto;
import com.sparta.community.comment.repository.CommentLikeRepository;
import com.sparta.community.comment.repository.CommentNoticeRepository;
import com.sparta.community.comment.repository.CommentPostRepository;
import com.sparta.community.follow.repository.FollowRepository;
import com.sparta.community.post.entity.Post;
import com.sparta.community.post.repository.PostLikeRepository;
import com.sparta.community.post.repository.PostRepository;
import com.sparta.community.user.dto.ProfileRequestDto;
import com.sparta.community.user.entity.User;
import com.sparta.community.user.entity.UserRoleEnum;
import com.sparta.community.user.repository.SignupAuthRepository;
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
    private final CommentPostRepository commentPostRepository;
    private final CommentNoticeRepository commentNoticeRepository;
    private final FollowRepository followRepository;
    private final SignupAuthRepository signupAuthRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final PostLikeRepository postLikeRepository;

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
    @Transactional
    public void updateUserProfile(Long id, ProfileRequestDto requestDto) {
        User userItem = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
        );
        userItem.setNickname(requestDto.getNickname());
        userItem.setOneLiner(requestDto.getOneLiner());
    }

    // 회원 권한 변경
    @Transactional
    public void updateUserRole(Long id, AdminUserRoleRequestDto requestDto) {
        User user = findUser(id);

        if (isAdmin(user)) {
            throw new IllegalArgumentException("관리자는 수정할 수 없습니다.");
        }

        if (requestDto.getRole().equals(user.getRole())) {
            throw new IllegalArgumentException("기존의 권한과 변경할 권한이 동일합니다.");
        }

        user.setRole(requestDto.getRole());
    }

    // 회원 탈퇴 : 좋아요, 팔로우, 댓글, 게시글, 이메일인증 데이터베이스 정리
    public void deleteUser(Long id) {
        User user = findUser(id);

        if (isAdmin(user)) {
            throw new IllegalArgumentException("관리자는 탈퇴할 수 없습니다.");
        }

        if (commentPostRepository.findByUserId(id).isPresent()) {
            commentPostRepository.deleteAll();
        }

        if (commentNoticeRepository.findByUserId(id).isPresent()) {
            commentNoticeRepository.deleteAll();
        }

        if (followRepository.findByFollowerUserId(id).isPresent()) {
            followRepository.deleteAll();
        }

        if (signupAuthRepository.findById(id).isPresent()) {
            signupAuthRepository.deleteById(id);
        }

        if (commentLikeRepository.findById(id).isPresent()) {
            commentLikeRepository.deleteById(id);
        }

        if (postLikeRepository.findById(id).isPresent()) {
            postLikeRepository.deleteById(id);
        }

        List<Post> postList = postRepository.findAllByUser(user);
        postRepository.deleteAll(postList);
        userRepository.delete(findUser(id));
    }

    // id 값으로 회원을 조회하는 메소드
    public User findUser(Long id) {
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
