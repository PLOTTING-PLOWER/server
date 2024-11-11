package com.plotting.server.plogging.presentation;

import com.plotting.server.global.dto.ResponseTemplate;
import com.plotting.server.plogging.application.MyPloggingService;
import com.plotting.server.plogging.dto.response.MyPloggingCreatedListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.plotting.server.global.dto.ResponseTemplate.EMPTY_RESPONSE;

@Tag(name = "MyPlogging", description = "내 플로깅 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/my-ploggings")
public class MyPloggingController {

    private final MyPloggingService myPloggingService;

    @Operation(summary = "내 플로깅 목록 조회", description = "내가 생성한 플로깅 목록을 조회합니다.")
    @GetMapping("/created")
    public ResponseEntity<ResponseTemplate<?>> getMyPloggingCreatedList(
            @RequestParam Long userId
    ) {

        MyPloggingCreatedListResponse response = myPloggingService.getMyPloggingCreatedList(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "내 플로깅 삭제", description = "내가 생성한 플로깅을 삭제합니다.")
    @DeleteMapping("/created/{ploggingId}")
    public ResponseEntity<ResponseTemplate<?>> deleteMyPlogging(
            @PathVariable Long ploggingId
    ) {

        myPloggingService.deleteMyPlogging(ploggingId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }
}