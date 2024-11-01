package com.plotting.server.comment.fixture;

import com.plotting.server.comment.domain.Comment;
import com.plotting.server.plogging.fixture.PloggingFixture;
import com.plotting.server.user.fixture.UserFixture;
import org.springframework.test.util.ReflectionTestUtils;

public class CommentFixture {
    public static final Comment PARENT_COMMENT = Comment.builder()
            .plogging(PloggingFixture.PLOGGING)
            .user(UserFixture.USER)
            .content("댓글 내용")
            .parentComment(null)
            .isCommentPublic(true)
            .build();

    public static final Comment CHILD_COMMENT = Comment.builder()
            .plogging(PloggingFixture.PLOGGING)
            .user(UserFixture.USER)
            .content("대댓글 내용")
            .parentComment(PARENT_COMMENT)
            .isCommentPublic(true)
            .build();

    static {
        ReflectionTestUtils.setField(PARENT_COMMENT, "id", 1L);
        ReflectionTestUtils.setField(CHILD_COMMENT, "id", 2L);
    }
}