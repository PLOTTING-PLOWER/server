package com.plotting.server.user.presentation;

import com.plotting.server.global.dto.ResponseTemplate;
import com.plotting.server.user.application.UserStarService;
import com.plotting.server.user.dto.response.MyUserStarListResponse;
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
@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage/star/users")
public class UserStarController {

    private final UserStarService userStarService; // 의존성 주입


    @Operation(summary = "즐겨 찾기 목록 조회", description = "내가 추가한 즐겨찾기 한 사람 목록을 조회합니다.")
    @GetMapping("/list")
    public ResponseEntity<ResponseTemplate<?>> getMyUserStarList(@RequestParam Long userId){
        MyUserStarListResponse response = userStarService.getMyUserStarList(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }
}
