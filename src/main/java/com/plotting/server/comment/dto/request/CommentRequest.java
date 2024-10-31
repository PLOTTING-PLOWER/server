package com.plotting.server.comment.dto.request;

import com.plotting.server.comment.domain.Comment;
import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.user.domain.User;
import lombok.Builder;

@Builder
public record CommentRequest(
        String content,
        Long depth,
        Long parentCommentId,
        Boolean isCommentPublic
) {
        public Comment toComment(Plogging plogging, User user, Comment parentComment, CommentRequest commentRequest) {
                return Comment.builder()
                        .plogging(plogging)
                        .user(user)
                        .content(commentRequest.content())
                        .depth(commentRequest.depth())
                        .parentComment(parentComment)
                        .isCommentPublic(commentRequest.isCommentPublic())
                        .build();
        }
}
