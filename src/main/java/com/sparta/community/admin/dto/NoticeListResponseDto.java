package com.sparta.community.admin.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class NoticeListResponseDto {
    private final List<NoticeResponseDto> noticeList;

    public NoticeListResponseDto(List<NoticeResponseDto> postList) {
        this.noticeList = postList;
    }
}