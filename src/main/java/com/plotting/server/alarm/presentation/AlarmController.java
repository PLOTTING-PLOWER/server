package com.plotting.server.alarm.presentation;

import com.plotting.server.global.dto.JwtUserDetails;
import com.plotting.server.global.dto.ResponseTemplate;
import com.plotting.server.user.application.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "alarm", description = "알람 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/alarm")
public class AlarmController {

    private final UserService userService;

    @Operation(summary = "fcm 토큰 저장", description = "fcm 토큰 저장")
    @PostMapping("/fcm-token")
    public ResponseEntity<ResponseTemplate<?>> saveFcmToken(
            @AuthenticationPrincipal JwtUserDetails jwtUserDetails,
            @RequestBody Map<String, String> request) {
        String token = request.get("token");
        userService.saveFcmToken(jwtUserDetails.userId(), token);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseTemplate.EMPTY_RESPONSE);
    }
}
