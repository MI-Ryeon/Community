package com.sparta.community.post.repository;

import com.sparta.community.post.entity.Post;
import com.sparta.community.post.entity.PostLike;
import com.sparta.community.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    PostLike findByUserAndPost(User user, Post post);
}
