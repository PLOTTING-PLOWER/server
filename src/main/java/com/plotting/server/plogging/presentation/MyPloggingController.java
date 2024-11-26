package com.plotting.server.plogging.presentation;

import com.plotting.server.global.dto.JwtUserDetails;
import com.plotting.server.global.dto.ResponseTemplate;
import com.plotting.server.plogging.application.MyPloggingService;
import com.plotting.server.plogging.dto.request.PloggingUpdateRequest;
import com.plotting.server.plogging.dto.response.MonthlyPloggingListResponse;
import com.plotting.server.plogging.dto.response.MyPloggingCreatedListResponse;
import com.plotting.server.plogging.dto.response.MyPloggingUserListResponse;
import com.plotting.server.plogging.application.MyPloggingHomeService;
import com.plotting.server.plogging.dto.response.MyPloggingParticipatedResponse;
import com.plotting.server.plogging.dto.response.MyPloggingScheduledResponse;
import com.plotting.server.plogging.dto.response.MyPloggingSummaryResponse;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.plotting.server.global.dto.ResponseTemplate.EMPTY_RESPONSE;

@Tag(name = "MyPlogging", description = "내 플로깅 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/my-ploggings")
public class MyPloggingController {

    private final MyPloggingService myPloggingService;
    private final MyPloggingHomeService myPloggingHomeService;


    @Operation(summary = "내가 만든 플로깅 목록 조회", description = "내가 만든 플로깅 목록을 조회합니다.")
    @GetMapping("/created")
    public ResponseEntity<ResponseTemplate<?>> getMyPloggingCreatedList(
            @AuthenticationPrincipal JwtUserDetails jwtUserDetails) {

        MyPloggingCreatedListResponse response = myPloggingService.getMyPloggingCreatedList(jwtUserDetails.userId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "플로깅 수정", description = "내가 만든 플로깅을 수정합니다.")
    @PatchMapping("/created/{ploggingId}")
    public ResponseEntity<ResponseTemplate<?>> updateMyPlogging(
            @PathVariable Long ploggingId,
            @RequestBody PloggingUpdateRequest request) {

        myPloggingService.updatePlogging(ploggingId, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }

    @Operation(summary = "플로깅 삭제", description = "내가 생성한 플로깅을 삭제합니다.")
    @DeleteMapping("/created/{ploggingId}")
    public ResponseEntity<ResponseTemplate<?>> deleteMyPlogging(
            @PathVariable Long ploggingId) {

        myPloggingService.deleteMyPlogging(ploggingId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }

    @Operation(summary = "승인 대기 멤버 조회", description = "승인 대기 멤버를 조회합니다.")
    @GetMapping("/{ploggingId}/waiting/users")
    public ResponseEntity<ResponseTemplate<?>> getMyPloggingUser(
            @PathVariable Long ploggingId) {

        MyPloggingUserListResponse response = myPloggingService.getMyPloggingUser(ploggingId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "플로깅 참가 승인", description = "플로깅 참가 신청을 승인합니다.")
    @PostMapping("/{ploggingId}/request/{ploggingUserId}")
    public ResponseEntity<ResponseTemplate<?>> updatePloggingUser(
            @PathVariable Long ploggingId,
            @PathVariable Long ploggingUserId) {

        String message = myPloggingService.updatePloggingUser(ploggingId, ploggingUserId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(message));
    }

    @Operation(summary = "플로깅 참가 거절", description = "플로깅 참가 신청을 거절합니다.")
    @DeleteMapping("/request/{ploggingUserId}")
    public ResponseEntity<ResponseTemplate<?>> deletePloggingUser(
            @PathVariable Long ploggingUserId) {

        myPloggingService.deletePloggingUser(ploggingUserId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }

    @Operation(summary = "월별 플로깅 통계 조회", description = "월별 플로깅 통계를 조회합니다.")
    @GetMapping("/months")
    public ResponseEntity<ResponseTemplate<?>> getMonthlyPlogging(
            @AuthenticationPrincipal JwtUserDetails jwtUserDetails) {

        MonthlyPloggingListResponse response = myPloggingService.getMonthlyPlogging(jwtUserDetails.userId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "참여했던 플로깅 조회", description = "참여했던 플로깅을 조회합니다.")
    @GetMapping("/participated")
    public ResponseEntity<ResponseTemplate<?>> getMyPloggingParticipated(
            @AuthenticationPrincipal JwtUserDetails jwtUserDetails) {

        List<MyPloggingParticipatedResponse> response = myPloggingHomeService.getParticipatedPloggings(jwtUserDetails.userId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }


    @Operation(summary = "예정된 플로깅 조회", description = "예정된 플로깅을 조회합니다.")
    @GetMapping("/scheduled")
    public ResponseEntity<ResponseTemplate<?>> getMyPloggingScheduled(
            @AuthenticationPrincipal JwtUserDetails jwtUserDetails) {

            // 서비스 호출을 통해 데이터를 조회
            List<MyPloggingScheduledResponse> response = myPloggingHomeService.getScheduledPloggings(jwtUserDetails.userId());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "나의 플로깅 횟수,시간 조회", description = "나의 플로깅 횟수,시간 조회합니다.")
    @GetMapping("/summary")
    public ResponseEntity<ResponseTemplate<?>> getPloggingSummary(
            @AuthenticationPrincipal JwtUserDetails jwtUserDetails) {

            MyPloggingSummaryResponse response = myPloggingHomeService.getPloggingSummary(jwtUserDetails.userId());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ResponseTemplate.from(response));
    }
}