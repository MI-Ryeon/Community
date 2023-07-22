package com.sparta.community.post.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PostListResponseDto {
    private final List<PostResponseDto> postsList;

    public PostListResponseDto(List<PostResponseDto> postList) {
        this.postsList = postList;
    }
}