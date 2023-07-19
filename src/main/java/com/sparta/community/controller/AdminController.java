package com.sparta.community.controller;

import com.sparta.community.dto.AdminUserListResponseDto;
import com.sparta.community.dto.AdminUserResponseDto;
import com.sparta.community.dto.ApiResponseDto;
import com.sparta.community.entity.UserRoleEnum;
import com.sparta.community.security.UserDetailsImpl;
import com.sparta.community.service.AdminService;
import com.sparta.community.service.CommentService;
import com.sparta.community.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Secured(UserRoleEnum.Authority.ADMIN)
@RestController
@RequestMapping("/it/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/users")
    public ResponseEntity<AdminUserListResponseDto> getUsers() {
        AdminUserListResponseDto result = adminService.getUsers();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<AdminUserResponseDto> getUserById(@PathVariable Long id) {
        AdminUserResponseDto result = adminService.getUserById(id);

        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<ApiResponseDto> deletePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        postService.deletePost(id, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("게시글 삭제 성공", HttpStatus.OK.value()));
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<ApiResponseDto> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        commentService.deleteComment(id, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("댓글 삭제 성공", HttpStatus.OK.value()));
    }
}
