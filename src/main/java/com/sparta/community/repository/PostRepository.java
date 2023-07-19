package com.sparta.community.repository;

import com.sparta.community.entity.Post;
import com.sparta.community.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByModifiedAtDesc();

    List<Post> findAllByUser(User user);
}