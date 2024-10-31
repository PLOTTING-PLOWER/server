package com.plotting.server.plogging.presentation;

import com.plotting.server.comment.application.CommentService;
import com.plotting.server.comment.dto.request.CommentRequest;
import com.plotting.server.global.dto.ResponseTemplate;
import com.plotting.server.plogging.application.PloggingService;
import com.plotting.server.plogging.application.PloggingServiceFacade;
import com.plotting.server.plogging.dto.response.PloggingDetailResponse;
import com.plotting.server.plogging.dto.response.PloggingUserListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

import static com.plotting.server.global.dto.ResponseTemplate.EMPTY_RESPONSE;

@Tag(name = "Plogging", description = "플로깅 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ploggings")
public class PloggingController {

    private final CommentService commentService;
    private final PloggingService ploggingService;
    private final PloggingServiceFacade ploggingServiceFacade;

    @Operation(summary = "플로깅 상세 조회", description = "플로깅 상세 조회 화면입니다.")
    @GetMapping("/{ploggingId}/info")
    public ResponseEntity<ResponseTemplate<?>> getPloggingDetail(
            @PathVariable Long ploggingId) {

        PloggingDetailResponse response = ploggingService.getPloggingDetail(ploggingId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "플로깅 참여자 조회", description = "플로깅 참여자 조회 화면입니다.")
    @GetMapping("/{ploggingId}/users")
    public ResponseEntity<ResponseTemplate<?>> getPloggingUserList(
            @PathVariable Long ploggingId) {

        PloggingUserListResponse response = ploggingService.getPloggingUserList(ploggingId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "플로깅 참여", description = "플로깅할 때 선착순이라면 즉시 승인, 승인제라면 승인 대기 상태가 됩니다.")
    @PostMapping("/{ploggingId}")
    public ResponseEntity<ResponseTemplate<?>> joinPlogging(
            @PathVariable Long ploggingId,
            @RequestParam Long userId) {

        String response = ploggingServiceFacade.joinPlogging(ploggingId, userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "플로깅 댓글 조회", description = "플로깅 댓글 조회 화면입니다.")
    @GetMapping("/{ploggingId}/comments")
    public ResponseEntity<ResponseTemplate<?>> getCommentList(
            @PathVariable Long ploggingId,
            @RequestParam Long userId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(commentService.getCommentList(ploggingId, userId)));
    }

    @Operation(summary = "플로깅 댓글 작성", description = "플로깅 댓글 작성 API 입니다.")
    @PostMapping("/{ploggingId}/comments")
    public ResponseEntity<ResponseTemplate<?>> saveComment(
            @PathVariable Long ploggingId,
            @RequestParam Long userId,
            @RequestBody CommentRequest request) {

        commentService.saveComment(ploggingId, userId, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }
}