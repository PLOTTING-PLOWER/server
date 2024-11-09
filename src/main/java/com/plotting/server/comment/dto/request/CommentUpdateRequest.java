package com.plotting.server.comment.dto.request;

import lombok.Builder;

@Builder
public record CommentUpdateRequest(
        String content,
        Boolean isCommentPublic
) {
}