package com.sparta.community.admin.controller;

import com.sparta.community.common.dto.ApiResponseDto;
import com.sparta.community.common.security.UserDetailsImpl;
import com.sparta.community.post.dto.PostRequestDto;
import com.sparta.community.post.dto.PostResponseDto;
import com.sparta.community.post.service.PostService;
import com.sparta.community.user.entity.UserRoleEnum;
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
public class AdminPostController {

    private final PostService postService;

    @PutMapping("/posts/{id}")
    public ResponseEntity<ApiResponseDto> updatePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        postService.updatePost(id, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("게시글 수정 완료", HttpStatus.OK.value()));
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<ApiResponseDto> deletePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        postService.deletePost(id, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("게시글 삭제 성공", HttpStatus.OK.value()));
    }
}
