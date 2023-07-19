package com.sparta.community.service;

import com.sparta.community.dto.FollowRequestDto;
import com.sparta.community.dto.PostResponseDto;
import com.sparta.community.entity.Follow;
import com.sparta.community.entity.Post;
import com.sparta.community.entity.User;
import com.sparta.community.repository.FollowRepository;
import com.sparta.community.repository.PostRepository;
import com.sparta.community.repository.UserRepository;
import com.sparta.community.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final PostRepository postRepository;

    public void followUser(UserDetailsImpl userDetails, FollowRequestDto followRequestDto) {
        User followerUser = userDetails.getUser();

        if (followerUser == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        User followingUser = userRepository.findByUsername(followRequestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        if (followerUser.getUsername().equals(followingUser.getUsername())) {
            throw new IllegalArgumentException("본인을 팔로우할 수 없습니다.");
        }

        if (followRepository.findByFollowerUserAndFollowingUser(followerUser, followingUser).isPresent()) {
            throw new IllegalArgumentException("이미 팔로우 했습니다.");
        }

        followRepository.save(new Follow(followingUser, followerUser));
    }

    public void unFollowUser(UserDetailsImpl userDetails, FollowRequestDto followRequestDto) {
        User followerUser = userDetails.getUser();

        if (followerUser == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        User followingUser = userRepository.findByUsername(followRequestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        Follow follow = followRepository.findByFollowerUserAndFollowingUser(followerUser, followingUser)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 팔로우하지 않았습니다."));

        followRepository.delete(follow);
    }

    public List<PostResponseDto> viewFollwingPostList(User user) {
        List<PostResponseDto> postList = new ArrayList<>();
        List<Follow> followList = followRepository.findAllByFollowerUser(user);
        List<User> userList = new ArrayList<>();

        for(Follow follow : followList) {
            userList.add(follow.getFollowingUser());
        }

        for(User foundUser : userList) {
            List<Post> foundPostList = postRepository.findAllByUser(foundUser);
            postList.addAll(foundPostList.stream().map(PostResponseDto :: new).toList());
        }

        return postList;
    }
}
