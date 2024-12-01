package com.plotting.server.comment.dto.response;

import com.plotting.server.user.domain.User;
import lombok.Builder;

import java.util.List;

@Builder
public record CommentListResponse(
        String nickname,
        String profileImageUrl,
        List<CommentResponse> comments
) {
    public static CommentListResponse from(User user, List<CommentResponse> comments) {
        return CommentListResponse.builder()
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .comments(comments)
                .build();
    }
}