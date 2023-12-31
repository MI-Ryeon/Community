package com.sparta.community.post.controller;

import com.sparta.community.common.dto.ApiResponseDto;
import com.sparta.community.common.security.UserDetailsImpl;
import com.sparta.community.post.dto.PostListResponseDto;
import com.sparta.community.post.dto.PostRequestDto;
import com.sparta.community.post.dto.PostResponseDto;
import com.sparta.community.post.service.PostService;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@RestController
@RequestMapping("/it")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<ApiResponseDto> createPost(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PostRequestDto requestDto) {
        postService.createPost(requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("게시글 생성 성공", HttpStatus.OK.value()));
    }

    @GetMapping("/posts")
    public ResponseEntity<PostListResponseDto> getPosts() {
        PostListResponseDto result = postService.getPosts();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id) {
        PostResponseDto result = postService.getPostById(id);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<ApiResponseDto> updatePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        try {
            postService.updatePost(id, requestDto, userDetails.getUser());
            return ResponseEntity.ok().body(new ApiResponseDto("게시글 수정 완료", HttpStatus.OK.value()));
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("작성자만 수정 할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<ApiResponseDto> deletePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        try {
            postService.deletePost(id, userDetails.getUser());
            return ResponseEntity.ok().body(new ApiResponseDto("게시글 삭제 성공", HttpStatus.OK.value()));
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("작성자만 삭제 할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PostMapping("/posts/{id}/likes")
    public ResponseEntity<ApiResponseDto> likePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        try {
            postService.likePost(userDetails, id);
        } catch (DuplicateRequestException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponseDto("게시글 좋아요 성공", HttpStatus.OK.value()));
    }

    @DeleteMapping("/posts/{id}/likes")
    public ResponseEntity<ApiResponseDto> deleteLikePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        try {
            postService.deleteLikePost(userDetails, id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("게시글 좋아요 취소 성공", HttpStatus.OK.value()));
    }
}