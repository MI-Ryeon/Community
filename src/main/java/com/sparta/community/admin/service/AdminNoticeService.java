package com.sparta.community.admin.service;

import com.sparta.community.admin.dto.NoticeListResponseDto;
import com.sparta.community.admin.entity.Notice;
import com.sparta.community.admin.repository.NoticeRepository;
import com.sparta.community.post.dto.NoticeResponseDto;
import com.sparta.community.post.dto.PostRequestDto;
import com.sparta.community.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminNoticeService {

    private final NoticeRepository noticeRepository;

    // 공지 작성
    public NoticeResponseDto createNotice(PostRequestDto requestDto, User user) {
        Notice notice = new Notice(requestDto);
        notice.setUser(user);

        noticeRepository.save(notice);

        return new NoticeResponseDto(notice);
    }

    // 공지 전체 조회
    public NoticeListResponseDto getNotice() {
        List<NoticeResponseDto> noticeList = noticeRepository.findAllByOrderByModifiedAtDesc().stream()
                .map(NoticeResponseDto::new)
                .collect(Collectors.toList());

        return new NoticeListResponseDto(noticeList);
    }

    // 공지 개별 조회
    public NoticeResponseDto getPostById(Long id) {
        Notice notice = findNotice(id);

        return new NoticeResponseDto(notice);
    }

    // id 값으로 공지를 조회하는 메소드
    public Notice findNotice(Long id) {
        return noticeRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
    }
}
