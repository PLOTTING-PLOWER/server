package com.plotting.server.comment.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.plotting.server.comment.domain.Comment;
import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.user.domain.User;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CommentResponse(
        Long ploggingId,
        Long commentId,
        String profileImageUrl,
        String nickname,
        String content,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime createdDate,
        Long depth,
        Long parentCommentId,
        Boolean isCommentPublic,
        Boolean isWriter
) {
    public static CommentResponse of(Plogging plogging, User user, Comment comment) {
        return CommentResponse.builder()
                .ploggingId(plogging.getId())
                .commentId(comment.getId())
                .profileImageUrl(comment.getUser().getProfileImageUrl())
                .nickname(comment.getUser().getNickname())
                .content(comment.getContent())
                .createdDate(comment.getCreatedDate())
                .depth(comment.getDepth())
                .parentCommentId(comment.getParentComment() != null ? comment.getParentComment().getId() : null)
                .isCommentPublic(comment.getIsCommentPublic())
                .isWriter(user.getId().equals(comment.getUser().getId()))
                .build();
    }
}