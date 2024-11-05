package com.plotting.server.comment.application;

import com.plotting.server.comment.domain.Comment;
import com.plotting.server.comment.dto.request.CommentRequest;
import com.plotting.server.comment.dto.request.CommentUpdateRequest;
import com.plotting.server.comment.dto.response.CommentListResponse;
import com.plotting.server.comment.dto.response.CommentResponse;
import com.plotting.server.comment.exception.CommentNotFoundException;
import com.plotting.server.comment.repository.CommentRepository;
import com.plotting.server.plogging.application.PloggingService;
import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.user.application.UserService;
import com.plotting.server.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.plotting.server.comment.exception.errorcode.CommentErrorCode.COMMENT_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PloggingService ploggingService;

    public CommentListResponse getCommentList(Long ploggingId, Long userId) {
        return CommentListResponse.from(
                commentRepository.findParentCommentsWithFetch(ploggingId)
                        .stream()
                        .map(comment -> CommentResponse.of(userId, comment))
                        .toList()
        );
    }

    @Transactional
    public void uploadComment(Long ploggingId, Long userId, CommentRequest commentRequest) {
        Plogging plogging = ploggingService.getPlogging(ploggingId);
        User user = userService.getUser(userId);

        Comment parentComment = commentRequest.parentCommentId() != 0L
                ? commentRepository.findById(commentRequest.parentCommentId())
                    .orElseThrow(() -> new CommentNotFoundException(COMMENT_NOT_FOUND))
                : null;

        commentRepository.save(commentRequest.toComment(plogging, user, parentComment));
    }

    @Transactional
    public void updateComment(Long commentId, CommentUpdateRequest request) {
        Comment comment = getComment(commentId);
        comment.updateComment(request);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = getComment(commentId);
        commentRepository.delete(comment);
    }

    private Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(COMMENT_NOT_FOUND));
    }
}
