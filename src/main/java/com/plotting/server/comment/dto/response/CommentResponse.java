package com.plotting.server.comment.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.plotting.server.comment.domain.Comment;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Builder
public record CommentResponse(
        Long commentId,
        String profileImageUrl,
        String nickname,
        String content,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime createdDate,
        Long depth,
        Long parentCommentId,
        Boolean isCommentPublic,
        Boolean isWriter,
        List<CommentResponse> childComments
) {
    public static CommentResponse of(Long userId, Comment comment) {
        return CommentResponse.builder()
                .commentId(comment.getId())
                .profileImageUrl(comment.getUser().getProfileImageUrl())
                .nickname(comment.getUser().getNickname())
                .content(comment.getContent())
                .createdDate(comment.getCreatedDate())
                .depth(comment.getDepth())
                .parentCommentId(comment.getParentComment() != null ? comment.getParentComment().getId() : null)
                .isCommentPublic(comment.getIsCommentPublic())
                .isWriter(Objects.equals(userId, comment.getUser().getId()))
                .childComments(
                        comment.getChildComments().stream()
                                .map(childComment -> CommentResponse.of(userId, childComment))
                                .toList()
                )
                .build();
    }
}