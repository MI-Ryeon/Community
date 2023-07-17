package com.sparta.community.repository;

import com.sparta.community.entity.Post;
import com.sparta.community.entity.PostLike;
import com.sparta.community.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    PostLike findByUserAndPost(User user, Post post);
}
