package com.plotting.server.plogging.presentation;

import com.plotting.server.global.dto.ResponseTemplate;
import com.plotting.server.plogging.application.MyPloggingHomeService;

import com.plotting.server.plogging.dto.response.MyPloggingParticipatedResponse;

import com.plotting.server.plogging.dto.response.MyPloggingScheduledResponse;
import com.plotting.server.plogging.dto.response.MyPloggingSummaryResponse;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.plotting.server.global.dto.ResponseTemplate.EMPTY_RESPONSE;

@Tag(name = "MyPlogging", description = "내 플로깅 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/my-ploggings")
public class MyPloggingController {


    private final MyPloggingHomeService myPloggingHomeService;



    @Operation(summary = "참여했던 플로깅 조회", description = "참여했던 플로깅을 조회합니다.")
    @GetMapping("/participated")
    public ResponseEntity<ResponseTemplate<?>> getMyPloggingParticipated(
            @RequestParam Long userId) {

        List<MyPloggingParticipatedResponse> response = myPloggingHomeService.getParticipatedPloggings(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }


    @Operation(summary = "예정된 플로깅 조회", description = "예정된 플로깅을 조회합니다.")
    @GetMapping("/scheduled")
    public ResponseEntity<ResponseTemplate<?>> getMyPloggingScheduled(@RequestParam Long userId) {
        try {
            // 요청받은 userId 출력
            System.out.println("scheduled ploggings for userId: " + userId);

            // 서비스 호출을 통해 데이터를 조회
            List<MyPloggingScheduledResponse> response = myPloggingHomeService.getScheduledPloggings(userId);

            if (response.isEmpty()) {
                // 조회된 데이터가 없을 때 출력
                System.out.println("No scheduled ploggings found for userId: " + userId);
            }

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ResponseTemplate.from(response));
        } catch (Exception e) {
            // 예외가 발생한 경우 출력
            System.out.println("Error fetching ploggings for userId: " + userId);
            e.printStackTrace(); // 예외 스택 트레이스 출력

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseTemplate.from("Error occurred"));
        }
    }

    @Operation(summary = "나의 플로깅 횟수,시간 조회", description = "나의 플로깅 횟수,시간 조회합니다.")
    @GetMapping("/summary")
    public ResponseEntity<ResponseTemplate<?>> getPloggingSummary(
            @RequestParam Long userId) {
        try {
            // 요청받은 userId 출력
            System.out.println("Summary ploggings for userId: " + userId);

            MyPloggingSummaryResponse response = myPloggingHomeService.getPloggingSummary(userId);

            if (response == null) {
                // 조회된 데이터가 없을 때 출력
                System.out.println("No Summary ploggings found for userId: " + userId);
            }

            // 성공적으로 데이터 조회
            System.out.println("Plogging data found for userId: " + userId);

            // response 객체 출력
            System.out.println("Response Data: " + response.toString());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ResponseTemplate.from(response));
        } catch (Exception e) {
            // 예외가 발생한 경우 출력
            System.out.println("Error fetching ploggings for userId: " + userId);
            e.printStackTrace(); // 예외 스택 트레이스 출력

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseTemplate.from("Error occurred"));
        }
    }
}