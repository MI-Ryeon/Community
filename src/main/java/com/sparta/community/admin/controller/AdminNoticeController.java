package com.sparta.community.admin.controller;

import com.sparta.community.admin.service.NoticeService;
import com.sparta.community.common.dto.ApiResponseDto;
import com.sparta.community.common.security.UserDetailsImpl;
import com.sparta.community.post.dto.NoticeResponseDto;
import com.sparta.community.post.dto.PostRequestDto;
import com.sparta.community.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Secured(UserRoleEnum.Authority.ADMIN)
@RestController
@RequestMapping("/it/admin")
@RequiredArgsConstructor
public class AdminNoticeController {

    private final NoticeService noticeService;

    @PostMapping("/notice")
    public ResponseEntity<ApiResponseDto> createNotice(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PostRequestDto requestDto) {
        NoticeResponseDto result = noticeService.createNotice(requestDto, userDetails.getUser());
        return ResponseEntity.status(201).body(result);
    }
}
