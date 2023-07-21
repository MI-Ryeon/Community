package com.sparta.community.post.repository;

import com.sparta.community.post.entity.Post;
import com.sparta.community.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByModifiedAtDesc();

    List<Post> findAllByUser(User user);
}