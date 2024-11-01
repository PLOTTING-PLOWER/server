package com.plotting.server.comment.application;

import com.plotting.server.comment.dto.response.CommentListResponse;
import com.plotting.server.comment.repository.CommentRepository;
import com.plotting.server.plogging.application.PloggingService;
import com.plotting.server.plogging.repository.PloggingRepository;
import com.plotting.server.user.application.UserService;
import com.plotting.server.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static com.plotting.server.comment.fixture.CommentFixture.CHILD_COMMENT;
import static com.plotting.server.comment.fixture.CommentFixture.PARENT_COMMENT;
import static com.plotting.server.plogging.fixture.PloggingFixture.PLOGGING;
import static com.plotting.server.user.fixture.UserFixture.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PloggingService ploggingService;

    @Mock
    private UserService userService;

    @Test
    @DisplayName("댓글 조회 테스트")
    void getCommentListTest() {
        // given
        given(ploggingService.getPlogging(PLOGGING.getId())).willReturn(PLOGGING);
        given(userService.getUser(USER.getId())).willReturn(USER);
        given(commentRepository.findCommentsByPloggingId(PLOGGING.getId())).willReturn(Arrays.asList(PARENT_COMMENT, CHILD_COMMENT));

        // when
        CommentListResponse response = commentService.getCommentList(PLOGGING.getId(), USER.getId());

        // then
        assertThat(response.comments()).hasSize(2);
    }
}
