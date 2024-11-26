package com.plotting.server.ranking.presentation;

import com.plotting.server.global.dto.JwtUserDetails;
import com.plotting.server.global.dto.ResponseTemplate;
import com.plotting.server.ranking.application.RankingService;
import com.plotting.server.ranking.dto.response.CombineRankingResponse;
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

@Tag(name = "Ranking", description = "랭킹 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ranking")
public class RankingController {
    private final RankingService rankingService;

    @Operation(summary = "랭킹 조회", description = "내 랭킹 + top 7 랭킹")
    @GetMapping("/list")
    public ResponseEntity<ResponseTemplate<?>> getRankingTop7(@AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
        CombineRankingResponse response  = CombineRankingResponse.of(rankingService.getTop7Rankings(), rankingService.getRankingResponse(jwtUserDetails.userId()));

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }
}
