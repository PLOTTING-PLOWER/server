package com.plotting.server.comment.dto.request;

public record CommentUpdateRequest(
        String content,
        Boolean isCommentPublic
) {
}