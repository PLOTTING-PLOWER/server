package com.plotting.server.user.presentation;

import com.plotting.server.global.dto.ResponseTemplate;
import com.plotting.server.user.application.UserStarService;
import com.plotting.server.user.dto.request.UserStarRequest;
import com.plotting.server.user.dto.response.MyUserStarListResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage/star/users")
public class UserStarController {

    private final UserStarService userStarService;

    @Operation(summary = "유저 즐겨 찾기 목록 조회", description = "내가 추가한 즐겨찾기 한 사람 목록을 조회합니다.")
    @GetMapping("/list")
    public ResponseEntity<ResponseTemplate<?>> getMyUserStarList(@RequestParam Long userId){
        MyUserStarListResponse response = userStarService.getMyUserStarList(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "유저 즐겨 찾기 해제", description = "내가 즐겨찾기 한 사람 해제")
    @DeleteMapping("/remove")
    public ResponseEntity<ResponseTemplate<?>> removeUserStar(@RequestBody UserStarRequest request){
        log.info("RemoveUserStarRequest: {}", request); // 요청 데이터 로그 출력
        userStarService.deleteUserStar(request.userId(), request.starUserId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }

    @Operation(summary = "유저 즐겨 찾기 추가", description = "내가 즐겨찾기 한 사람 해제")
    @PostMapping("/add")
    public ResponseEntity<ResponseTemplate<?>> addUserStar(@RequestBody UserStarRequest request){
        log.info("addUserStarRequest: {}", request); // 요청 데이터 로그 출력
        userStarService.addUserStar(request.userId(), request.starUserId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }
}
