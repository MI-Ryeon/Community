package com.sparta.community.admin.controller;

import com.sparta.community.admin.dto.NoticeListResponseDto;
import com.sparta.community.admin.service.AdminNoticeService;
import com.sparta.community.common.dto.ApiResponseDto;
import com.sparta.community.common.security.UserDetailsImpl;
import com.sparta.community.post.dto.NoticeResponseDto;
import com.sparta.community.post.dto.PostRequestDto;
import com.sparta.community.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/it/admin")
@RequiredArgsConstructor
public class AdminNoticeController {

    private final AdminNoticeService adminNoticeService;

    // 공지 작성
    @Secured(UserRoleEnum.Authority.ADMIN)
    @PostMapping("/notice")
    public ResponseEntity<ApiResponseDto> createNotice(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PostRequestDto requestDto) {
        adminNoticeService.createNotice(requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("공지 작성 완료", HttpStatus.OK.value()));
    }

    // 공지 전체 조회
    @GetMapping("/notice")
    public ResponseEntity<NoticeListResponseDto> getNotice() {
        NoticeListResponseDto result = adminNoticeService.getNotice();
        return ResponseEntity.ok().body(result);
    }

    // 공지 개별 조회
    @GetMapping("/notice/{id}")
    public ResponseEntity<NoticeResponseDto> getNoticeById(@PathVariable Long id) {
        NoticeResponseDto result = adminNoticeService.getPostById(id);
        return ResponseEntity.ok().body(result);
    }
}
