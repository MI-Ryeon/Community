package com.sparta.community.admin.entity;

import com.sparta.community.comment.entity.Comment;
import com.sparta.community.common.dto.TimeStamped;
import com.sparta.community.post.dto.PostRequestDto;
import com.sparta.community.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "notice")
public class Notice extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "notice", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    public Notice(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }
}