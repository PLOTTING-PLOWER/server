package com.plotting.server.comment.presentation;

import com.plotting.server.comment.application.CommentService;
import com.plotting.server.comment.dto.request.CommentRequest;
import com.plotting.server.comment.dto.request.CommentUpdateRequest;
import com.plotting.server.comment.dto.response.CommentListResponse;
import com.plotting.server.global.dto.JwtUserDetails;
import com.plotting.server.global.dto.ResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import static com.plotting.server.global.dto.ResponseTemplate.EMPTY_RESPONSE;

@Tag(name = "Comment", description = "댓글 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ploggings")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "플로깅 댓글 조회", description = "플로깅 댓글 조회 화면입니다.")
    @GetMapping("/{ploggingId}/comments")
    public ResponseEntity<ResponseTemplate<?>> getCommentList(
            @PathVariable Long ploggingId,
            @AuthenticationPrincipal JwtUserDetails jwtUserDetails) {

        CommentListResponse response = commentService.getCommentList(ploggingId, jwtUserDetails.userId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "플로깅 댓글 작성", description = "플로깅 댓글 작성 API 입니다.")
    @PostMapping("/{ploggingId}/comments")
    public ResponseEntity<ResponseTemplate<?>> saveComment(
            @PathVariable Long ploggingId,
            @AuthenticationPrincipal JwtUserDetails jwtUserDetails,
            @RequestBody CommentRequest request) {

        commentService.uploadComment(ploggingId, jwtUserDetails.userId(), request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }

    @Operation(summary = "플로깅 댓글 수정", description = "플로깅 댓글 수정 API 입니다.")
    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<ResponseTemplate<?>> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentUpdateRequest request) {

        commentService.updateComment(commentId, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }

    @Operation(summary = "플로깅 댓글 삭제", description = "플로깅 댓글 삭제 API 입니다.")
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ResponseTemplate<?>> deleteComment(
            @PathVariable Long commentId) {

        commentService.deleteComment(commentId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }
}