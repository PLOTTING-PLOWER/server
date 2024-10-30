package com.plotting.server.comment.repository.init;

import com.plotting.server.comment.domain.Comment;
import com.plotting.server.comment.repository.CommentRepository;
import com.plotting.server.global.util.DummyDataInit;
import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.repository.PloggingRepository;
import com.plotting.server.user.domain.User;
import com.plotting.server.user.exception.UserNotFoundException;
import com.plotting.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

import static com.plotting.server.user.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Order(3)
@DummyDataInit
public class CommentInitializer implements ApplicationRunner {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PloggingRepository ploggingRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (commentRepository.count() > 0) {
            log.info("[Comment]더미 데이터 존재");
        } else {
            User user1 = userRepository.findById(1L)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
            User user2 = userRepository.findById(2L)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
            User user3 = userRepository.findById(3L)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

            Plogging plogging1 = ploggingRepository.findById(1L)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
            Plogging plogging2 = ploggingRepository.findById(2L)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

            List<Comment> commentList = new ArrayList<>();

            Comment DUMMY_COMMENT1 = Comment.builder()
                    .user(user1)
                    .plogging(plogging1)
                    .parentComment(null)
                    .content("늦참해도 되나요??")
                    .depth(0L)
                    .isCommentPublic(true)
                    .build();

            Comment DUMMY_COMMENT2 = Comment.builder()
                    .user(user2)
                    .plogging(plogging1)
                    .parentComment(DUMMY_COMMENT1)
                    .content("네 가능합니다!")
                    .depth(1L)
                    .isCommentPublic(true)
                    .build();

            Comment DUMMY_COMMENT3 = Comment.builder()
                    .user(user3)
                    .plogging(plogging1)
                    .parentComment(null)
                    .content("끝나고 뒷풀이 하실 분?")
                    .depth(0L)
                    .isCommentPublic(true)
                    .build();

            Comment DUMMY_COMMENT4 = Comment.builder()
                    .user(user1)
                    .plogging(plogging1)
                    .parentComment(DUMMY_COMMENT3)
                    .content("저요!")
                    .depth(1L)
                    .isCommentPublic(true)
                    .build();

            Comment DUMMY_COMMENT5 = Comment.builder()
                    .user(user2)
                    .plogging(plogging2)
                    .parentComment(null)
                    .content("플로깅 같이 하실 분?")
                    .depth(0L)
                    .isCommentPublic(true)
                    .build();

            Comment DUMMY_COMMENT6 = Comment.builder()
                    .user(user3)
                    .plogging(plogging2)
                    .parentComment(DUMMY_COMMENT5)
                    .content("저요!")
                    .depth(1L)
                    .isCommentPublic(true)
                    .build();

            commentList.add(DUMMY_COMMENT1);
            commentList.add(DUMMY_COMMENT2);
            commentList.add(DUMMY_COMMENT3);
            commentList.add(DUMMY_COMMENT4);
            commentList.add(DUMMY_COMMENT5);
            commentList.add(DUMMY_COMMENT6);

            commentRepository.saveAll(commentList);
        }
    }
}