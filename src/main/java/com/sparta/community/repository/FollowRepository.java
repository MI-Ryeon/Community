package com.sparta.community.repository;

import com.sparta.community.entity.Follow;
import com.sparta.community.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowerUserAndFollowingUser(User followerUser, User followingUser);

    List<Follow> findAllByFollowerUser(User user);
}
