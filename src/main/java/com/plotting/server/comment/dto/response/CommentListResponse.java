package com.plotting.server.comment.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CommentListResponse(
        List<CommentResponse> comments
) {
    public static CommentListResponse from(List<CommentResponse> comments) {
        return CommentListResponse.builder()
                .comments(comments)
                .build();
    }
}