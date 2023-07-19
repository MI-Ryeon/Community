package com.sparta.community.dto;

import com.sparta.community.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDto extends ApiResult {
    private String username;
    private String comment;
    private Integer likeComment;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        super();
        this.comment = comment.getBody();
        this.username = comment.getUser().getUsername();
        this.likeComment = comment.getCommentLikes().size();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}