package com.plotting.server.plogging.presentation;

import com.plotting.server.global.dto.ResponseTemplate;
import com.plotting.server.plogging.application.PloggingStarService;
import com.plotting.server.plogging.dto.response.MyPloggingStarListResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/mypage/star/plogging")
public class PloggingStarController {

    private final PloggingStarService ploggingStarService;

    @Operation(summary = "플로깅 즐겨 찾기 목록 조회", description = "내가 추가한 즐겨찾기 한 플로깅 목록을 조회합니다.")
    @GetMapping("/list")
    public ResponseEntity<ResponseTemplate<?>> getMyPloggingStarList(@RequestParam Long userId){
        MyPloggingStarListResponse response = ploggingStarService.getMyPloggingStarList(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }
}
