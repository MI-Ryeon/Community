package com.sparta.community.comment.repository;

import com.sparta.community.comment.entity.Comment;
import com.sparta.community.comment.entity.CommentLike;
import com.sparta.community.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    CommentLike findByUserAndComment(User user, Comment comment);
}