package com.sparta.community.admin.controller;

import com.sparta.community.comment.dto.CommentRequestDto;
import com.sparta.community.comment.dto.CommentResponseDto;
import com.sparta.community.comment.service.CommentNoticeService;
import com.sparta.community.common.dto.ApiResponseDto;
import com.sparta.community.common.security.UserDetailsImpl;
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
public class AdminCommentController {

    private final CommentNoticeService commentNoticeService;

    // 등록된 댓글 수정
    @PutMapping("/comments/{id}")
    public ResponseEntity<ApiResponseDto> updateNoticeComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody CommentRequestDto requestDto) {
        CommentResponseDto result = commentNoticeService.updateNoticeComment(id, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(result);
    }

    // 등록된 댓글 삭제
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<ApiResponseDto> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        commentNoticeService.deleteNoticeComment(id, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("댓글 삭제 성공", HttpStatus.OK.value()));
    }
}
