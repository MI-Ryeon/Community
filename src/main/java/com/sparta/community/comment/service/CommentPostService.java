package com.sparta.community.comment.service;

import com.sparta.community.comment.dto.CommentRequestDto;
import com.sparta.community.comment.dto.CommentResponseDto;
import com.sparta.community.comment.entity.Comment;
import com.sparta.community.comment.entity.CommentLike;
import com.sparta.community.comment.repository.CommentLikeRepository;
import com.sparta.community.comment.repository.CommentPostRepository;
import com.sparta.community.common.security.UserDetailsImpl;
import com.sparta.community.post.entity.Post;
import com.sparta.community.post.service.PostService;
import com.sparta.community.user.entity.User;
import com.sparta.community.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class CommentPostService {
    private final PostService postService;
    private final CommentPostRepository commentPostRepository;
    private final CommentLikeRepository commentLikeRepository;

    public CommentResponseDto createPostComment(CommentRequestDto requestDto, User user) {
        Post post = postService.findPost(requestDto.getPostId());
        Comment comment = new Comment(requestDto.getComment());
        comment.setUser(user);
        comment.setPost(post);

        var savedComment = commentPostRepository.save(comment);

        return new CommentResponseDto(savedComment);
    }

    public void deletePostComment(Long id, User user) {
        Comment comment = commentPostRepository.findById(id).orElseThrow();

        // 요청자가 운영자 이거나 댓글 작성자(post.user) 와 요청자(user) 가 같은지 체크
        if (!user.getRole().equals(UserRoleEnum.ADMIN) && !comment.getUser().equals(user)) {
            throw new RejectedExecutionException();
        }

        commentPostRepository.delete(comment);
    }

    @Transactional
    public CommentResponseDto updatePostComment(Long id, CommentRequestDto requestDto, User user) {
        Comment comment = commentPostRepository.findById(id).orElseThrow();

        // 요청자가 운영자 이거나 댓글 작성자(post.user) 와 요청자(user) 가 같은지 체크
        if (!user.getRole().equals(UserRoleEnum.ADMIN) && !comment.getUser().equals(user)) {
            throw new RejectedExecutionException();
        }

        comment.setBody(requestDto.getComment());

        return new CommentResponseDto(comment);
    }

    public void likePostComment(UserDetailsImpl userDetails, Long id) {
        User user = userDetails.getUser();

        if (user == null) {
            throw new RejectedExecutionException("사용자를 찾을 수 없습니다.");
        }

        Comment comment = commentPostRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        if (user.getId().equals(comment.getUser().getId())) {
            throw new RejectedExecutionException("본인의 게시글엔 좋아요를 할 수 없습니다.");
        }

        CommentLike commentLike = commentLikeRepository.findByUserAndComment(user, comment);
        if (commentLike != null) {
            throw new RejectedExecutionException("이미 좋아요를 눌렀습니다.");
        }

        commentLikeRepository.save(new CommentLike(user, comment));
    }

    public void deleteLikePostComment(UserDetailsImpl userDetails, Long id) {
        User user = userDetails.getUser();

        Comment comment = commentPostRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        if (user == null) {
            throw new RejectedExecutionException("사용자를 찾을 수 없습니다.");
        }

        CommentLike commentLike = commentLikeRepository.findByUserAndComment(user, comment);

        if (commentLike == null) {
            throw new RejectedExecutionException("좋아요를 누르지 않았습니다.");
        }

        if (this.checkValidUser(user, commentLike)) {
            throw new RejectedExecutionException("본인의 좋아요만 취소할 수 있습니다.");
        }

        commentLikeRepository.delete(commentLike);
    }

    private boolean checkValidUser(User user, CommentLike commentLike) {
        boolean result = !(user.getId().equals(commentLike.getUser().getId())) && !(user.getRole().equals(UserRoleEnum.ADMIN));
        return result;
    }
}
