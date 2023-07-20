package com.sparta.community.comment.service;

import com.sparta.community.admin.entity.Notice;
import com.sparta.community.admin.service.NoticeService;
import com.sparta.community.comment.dto.CommentRequestDto;
import com.sparta.community.comment.dto.CommentResponseDto;
import com.sparta.community.comment.entity.Comment;
import com.sparta.community.comment.entity.CommentLike;
import com.sparta.community.comment.repository.CommentNoticeRepository;
import com.sparta.community.user.entity.User;
import com.sparta.community.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class CommentNoticeService {

    private final NoticeService noticeService;
    private final CommentNoticeRepository commentPostRepository;

    public CommentResponseDto createNoticeComment(CommentRequestDto requestDto, User user) {
        Notice notice = noticeService.findNotice(requestDto.getPostId());
        Comment comment = new Comment(requestDto.getComment());
        comment.setUser(user);
        comment.setNotice(notice);

        var savedComment = commentPostRepository.save(comment);

        return new CommentResponseDto(savedComment);
    }

    public void deleteNoticeComment(Long id, User user) {
        Comment comment = commentPostRepository.findById(id).orElseThrow();

        // 요청자가 운영자 이거나 댓글 작성자(post.user) 와 요청자(user) 가 같은지 체크
        if (!user.getRole().equals(UserRoleEnum.ADMIN) && !comment.getUser().equals(user)) {
            throw new RejectedExecutionException();
        }

        commentPostRepository.delete(comment);
    }

    @Transactional
    public CommentResponseDto updateNoticeComment(Long id, CommentRequestDto requestDto, User user) {
        Comment comment = commentPostRepository.findById(id).orElseThrow();

        // 요청자가 운영자 이거나 댓글 작성자(post.user) 와 요청자(user) 가 같은지 체크
        if (!user.getRole().equals(UserRoleEnum.ADMIN) && !comment.getUser().equals(user)) {
            throw new RejectedExecutionException();
        }

        comment.setBody(requestDto.getComment());

        return new CommentResponseDto(comment);
    }
}
