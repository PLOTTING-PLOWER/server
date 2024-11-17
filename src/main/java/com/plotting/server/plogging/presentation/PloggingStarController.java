package com.plotting.server.plogging.presentation;

import com.plotting.server.global.dto.ResponseTemplate;
import com.plotting.server.plogging.application.PloggingStarService;
import com.plotting.server.plogging.dto.request.PloggingStarRequest;
import com.plotting.server.plogging.dto.response.MyPloggingStarListResponse;
import com.plotting.server.user.dto.request.UserStarRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "플로깅 즐겨 찾기 해제", description = "내가 즐겨찾기 한 플로깅 해제")
    @DeleteMapping("/remove")
    public ResponseEntity<ResponseTemplate<?>> removePloggingStar(@RequestBody PloggingStarRequest request){
        log.info("RemoveUserStarRequest: {}", request); // 요청 데이터 로그 출력
        ploggingStarService.deleteUserStar(request.userId(), request.ploggingId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }
}
