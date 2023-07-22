package com.sparta.community.comment.repository;

import com.sparta.community.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentNoticeRepository extends JpaRepository<Comment, Long> {
}
