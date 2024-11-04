package com.plotting.server.comment.application;

import com.plotting.server.comment.dto.request.CommentRequest;
import com.plotting.server.comment.dto.request.CommentUpdateRequest;
import com.plotting.server.comment.dto.response.CommentListResponse;
import com.plotting.server.comment.repository.CommentRepository;
import com.plotting.server.plogging.application.PloggingService;
import com.plotting.server.user.application.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static com.plotting.server.comment.fixture.CommentFixture.CHILD_COMMENT;
import static com.plotting.server.comment.fixture.CommentFixture.PARENT_COMMENT;
import static com.plotting.server.plogging.fixture.PloggingFixture.PLOGGING;
import static com.plotting.server.user.fixture.UserFixture.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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

    @Test
    @DisplayName("댓글 작성 테스트")
    void saveCommentTest() {
        // given
        CommentRequest request = CommentRequest.builder()
                .content("대댓글 내용")
                .parentCommentId(PARENT_COMMENT.getId())
                .isCommentPublic(true)
                .build();

        given(ploggingService.getPlogging(PLOGGING.getId())).willReturn(PLOGGING);
        given(userService.getUser(USER.getId())).willReturn(USER);
        given(commentRepository.findById(PARENT_COMMENT.getId())).willReturn(Optional.of(PARENT_COMMENT));

        // when
        commentService.saveComment(PLOGGING.getId(), USER.getId(), request);

        // then
        assertThat(CHILD_COMMENT.getId()).isNotNull();
    }

    @Test
    @DisplayName("댓글 수정 테스트")
    void updateCommentTest() {
        // given
        CommentUpdateRequest request = CommentUpdateRequest.builder()
                .content("수정된 댓글 내용")
                .isCommentPublic(true)
                .build();

        given(commentRepository.findById(CHILD_COMMENT.getId())).willReturn(Optional.of(CHILD_COMMENT));

        // when
        commentService.updateComment(CHILD_COMMENT.getId(), request);

        // then
        assertThat(CHILD_COMMENT.getContent()).isEqualTo(request.content());
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    void deleteCommentTest() {
        // given
        given(commentRepository.findById(CHILD_COMMENT.getId())).willReturn(Optional.of(CHILD_COMMENT));

        // when
        commentService.deleteComment(CHILD_COMMENT.getId());

        // then
        verify(commentRepository).delete(CHILD_COMMENT);
    }
}
