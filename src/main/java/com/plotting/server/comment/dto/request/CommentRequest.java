package com.plotting.server.comment.dto.request;

import com.plotting.server.comment.domain.Comment;
import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.user.domain.User;
import lombok.Builder;

@Builder
public record CommentRequest(
        String content,
        Long parentCommentId,
        Long depth,
        Boolean isCommentPublic
) {
        public Comment toComment(Plogging plogging, User user, Comment parentComment) {
                return Comment.builder()
                        .plogging(plogging)
                        .user(user)
                        .parentComment(parentComment)
                        .content(content)
                        .depth(depth)
                        .isCommentPublic(isCommentPublic)
                        .build();
        }
}
