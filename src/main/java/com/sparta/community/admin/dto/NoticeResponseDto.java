package com.sparta.community.post.dto;

import com.sparta.community.admin.entity.Notice;
import com.sparta.community.comment.dto.CommentResponseDto;
import com.sparta.community.common.dto.ApiResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
public class NoticeResponseDto extends ApiResponseDto {
    private Long id;
    private String username;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Integer likePost;
    private List<CommentResponseDto> comments;

    public NoticeResponseDto(Notice notice) {
        this.id = notice.getId();
        this.title = notice.getTitle();
        this.username = notice.getUser().getUsername();
        this.content = notice.getContent();
        this.createdAt = notice.getCreatedAt();
        this.modifiedAt = notice.getModifiedAt();
        this.comments = notice.getComments().stream()
                .map(CommentResponseDto::new)
                .sorted(Comparator.comparing(CommentResponseDto::getCreatedAt).reversed()) // 작성날짜 내림차순
                .toList();
    }
}