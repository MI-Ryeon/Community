package com.sparta.community.repository;

import com.sparta.community.entity.Comment;
import com.sparta.community.entity.CommentLike;
import com.sparta.community.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    CommentLike findByUserAndComment(User user, Comment comment);
}