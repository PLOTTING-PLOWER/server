package com.plotting.server.plogging.presentation;

import com.plotting.server.global.dto.JwtUserDetails;
import com.plotting.server.global.dto.ResponseTemplate;
import com.plotting.server.plogging.application.PloggingStarService;
import com.plotting.server.plogging.dto.response.MyPloggingStarListResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/star/plogging")
public class PloggingStarController {

    private final PloggingStarService ploggingStarService;

    @Operation(summary = "플로깅 즐겨 찾기 목록 조회", description = "내가 추가한 즐겨찾기 한 플로깅 목록을 조회합니다.")
    @GetMapping("/list")
    public ResponseEntity<ResponseTemplate<?>> getMyPloggingStarList(@AuthenticationPrincipal JwtUserDetails jwtUserDetails){
        MyPloggingStarListResponse response = ploggingStarService.getMyPloggingStarList(jwtUserDetails.userId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "플로깅 즐겨 찾기 업데이트", description = "내가 즐겨찾기 한 사람 업데이트")
    @PostMapping("/update/{ploggingId}")
    public ResponseEntity<ResponseTemplate<?>> updatePloggingStar(
            @AuthenticationPrincipal JwtUserDetails jwtUserDetails,
            @PathVariable Long ploggingId){
        ploggingStarService.updatePloggingStar(jwtUserDetails.userId(), ploggingId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }

}
