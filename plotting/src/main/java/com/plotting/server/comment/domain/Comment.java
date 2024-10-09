package com.plotting.server.comment.domain;


import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Table(name = "comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "plogging_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Plogging plogging;

    @JoinColumn(name = "parent_comment_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment parentComment;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_date", nullable = false)
    private Timestamp createdDate;

    @Column(name = "modified_date")
    private Timestamp modifiedDate;

    @Column(name = "depth", nullable = false)
    private Long depth;

    @Column(name = "is_comment_public", nullable = false)
    private Boolean isCommentPublic;

    @Builder
    public Comment(User user, Plogging plogging,Comment parentComment, String content, Timestamp createdDate,Timestamp modifiedDate, Long depth, Boolean isCommentPublic) {
        this.user = user;
        this.plogging = plogging;
        this.parentComment = parentComment;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.depth = depth;
        this.isCommentPublic = isCommentPublic;
    }

    // 부모 댓글 설정
    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

}
