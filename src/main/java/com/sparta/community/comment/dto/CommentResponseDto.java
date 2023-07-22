package com.sparta.community.comment.dto;

import com.sparta.community.comment.entity.Comment;
import com.sparta.community.user.entity.UserRoleEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDto {
    private String username;
    private UserRoleEnum role;
    private String comment;
    private Integer likeComment;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        super();
        this.comment = comment.getBody();
        this.role = comment.getUser().getRole();
        this.username = comment.getUser().getUsername();
        this.likeComment = comment.getCommentLikes().size();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}