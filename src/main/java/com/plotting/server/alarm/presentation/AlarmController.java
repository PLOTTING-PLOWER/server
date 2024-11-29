package com.plotting.server.alarm.presentation;

import com.plotting.server.alarm.application.AlarmService;
import com.plotting.server.alarm.application.FcmService;
import com.plotting.server.alarm.dto.request.AlarmRequest;
import com.plotting.server.alarm.dto.response.AlarmListResponse;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "alarm", description = "알람 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/alarm")
public class AlarmController {

    private final UserService userService;
    private final AlarmService alarmService;

    @Operation(summary = "fcm 토큰 저장", description = "fcm 토큰 저장")
    @PostMapping("/fcm-token")
    public ResponseEntity<ResponseTemplate<?>> saveFcmToken(
            @AuthenticationPrincipal JwtUserDetails jwtUserDetails,
            @RequestBody AlarmRequest request) {
        String token = request.token();
        userService.saveFcmToken(jwtUserDetails.userId(), token);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseTemplate.EMPTY_RESPONSE);
    }

    @Operation(summary = "알림 조회", description = "알림 조회 페이지")
    @GetMapping("/list")
    public ResponseEntity<ResponseTemplate<?>> getAlarmList(@AuthenticationPrincipal JwtUserDetails jwtUserDetails){

        AlarmListResponse alarmListResponse = alarmService.getAlarmList(jwtUserDetails.userId());
        return ResponseEntity.status(HttpStatus.OK).body(ResponseTemplate.from(alarmListResponse));
    }

}
