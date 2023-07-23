package com.sparta.community.comment.repository;

import com.sparta.community.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentPostRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByUserId(Long id);
}