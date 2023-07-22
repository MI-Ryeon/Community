package com.sparta.community.admin.dto;

import com.sparta.community.post.dto.NoticeResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class NoticeListResponseDto {
    private final List<NoticeResponseDto> noticeList;

    public NoticeListResponseDto(List<NoticeResponseDto> postList) {
        this.noticeList = postList;
    }
}