package com.plotting.server.comment.application;

import com.plotting.server.comment.dto.response.CommentListResponse;
import com.plotting.server.comment.dto.response.CommentResponse;
import com.plotting.server.comment.repository.CommentRepository;
import com.plotting.server.plogging.application.PloggingService;
import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.user.application.UserService;
import com.plotting.server.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PloggingService ploggingService;

    public CommentListResponse getCommentList(Long ploggingId, Long userId) {
        Plogging plogging = ploggingService.getPlogging(ploggingId);
        User user = userService.getUser(userId);

        return CommentListResponse.from(
                commentRepository.findCommentsByPloggingId(ploggingId)
                        .stream()
                        .map(comment -> CommentResponse.of(plogging, user, comment))
                        .toList()
        );
    }
}
