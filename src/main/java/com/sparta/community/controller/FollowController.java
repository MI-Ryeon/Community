package com.sparta.community.controller;

import com.sparta.community.dto.ApiResult;
import com.sparta.community.dto.FollowRequestDto;
import com.sparta.community.dto.PostResponseDto;
import com.sparta.community.security.UserDetailsImpl;
import com.sparta.community.service.FollowService;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/it")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/following")
    public ResponseEntity<ApiResult> followUser(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody FollowRequestDto followRequestDto) {
        try {
            followService.followUser(userDetails, followRequestDto);
        } catch (DuplicateRequestException e) {
            return ResponseEntity.badRequest().body(new ApiResult(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok().body(new ApiResult("팔로우 성공", HttpStatus.OK.value()));
    }

    @DeleteMapping("/following")
    public ResponseEntity<ApiResult> unFollowUser(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody FollowRequestDto followRequestDto) {
        try {
            followService.unFollowUser(userDetails, followRequestDto);
        } catch (DuplicateRequestException e) {
            return ResponseEntity.badRequest().body(new ApiResult(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok().body(new ApiResult("언팔로우 성공", HttpStatus.OK.value()));
    }

    @GetMapping("/following/posts")
    public List<PostResponseDto> postList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return followService.viewFollwingPostList(userDetails.getUser());
    }
}
