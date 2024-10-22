package com.plotting.server.plogging.presentation;

import com.plotting.server.global.dto.ResponseTemplate;
import com.plotting.server.plogging.application.PloggingFilterService;
import com.plotting.server.plogging.application.PloggingService;
import com.plotting.server.plogging.application.PloggingServiceFacade;
import com.plotting.server.plogging.domain.type.PloggingType;
import com.plotting.server.plogging.dto.response.PloggingDetailResponse;
import com.plotting.server.plogging.dto.response.PloggingResponse;
import com.plotting.server.plogging.dto.response.PloggingUserListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Plogging", description = "플로깅 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ploggings")
public class PloggingController {

    private final PloggingService ploggingService;
    private final PloggingServiceFacade ploggingServiceFacade;
    private final PloggingFilterService ploggingFilterService;  //구현 후 service 로직 이동시키고 삭제할 수도 있음.

    @Operation(summary = "플로깅 리스트 조회", description = "플로깅 리스트 조회 화면입니다.<br> region: 서울, startDate: 2024-10-01, endDate: 2025-10-01, type: DIRECT, spendTime: 60, startTime: 2024-10-01T10:00:00, maxPeople: 10")
    @GetMapping("/filter")
    public ResponseEntity<ResponseTemplate<?>> findListByFilter(@RequestParam(defaultValue = "서울") String region,
                                                                @RequestParam(defaultValue = "2024-10-01") LocalDate startDate,
                                                                @RequestParam(defaultValue = "2025-10-01") LocalDate endDate,
                                                                @RequestParam PloggingType type,
                                                                @RequestParam Long spendTime,
                                                                @RequestParam(defaultValue = "2024-10-01T10:00:00") LocalDateTime startTime,
                                                                @RequestParam(defaultValue = "1000") Long maxPeople) {

        List<PloggingResponse> response = ploggingFilterService.findListByFilter(region, startDate, endDate, type, spendTime, startTime, maxPeople);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

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
    @PostMapping("/{ploggingId}/{userId}")
    public ResponseEntity<ResponseTemplate<?>> joinPlogging(
            @PathVariable Long ploggingId,
            @PathVariable Long userId) {

        String response = ploggingServiceFacade.joinPlogging(ploggingId, userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }
}