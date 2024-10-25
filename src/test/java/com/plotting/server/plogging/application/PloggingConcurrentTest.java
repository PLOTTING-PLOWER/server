package com.plotting.server.plogging.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.repository.PloggingRepository;
import com.plotting.server.plogging.repository.PloggingUserRepository;
import com.plotting.server.user.domain.User;
import com.plotting.server.user.domain.UserType.Role;
import com.plotting.server.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StopWatch;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.plotting.server.plogging.domain.type.PloggingType.DIRECT;
import static com.plotting.server.plogging.util.PloggingConstants.DIRECT_COMPLETE;
import static com.plotting.server.plogging.util.PloggingConstants.FULL_RECRUIT;
import static com.plotting.server.user.domain.UserType.LoginType.SELF;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class PloggingConcurrentTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PloggingRepository ploggingRepository;

    @Autowired
    private PloggingUserRepository ploggingUserRepository;

    @Autowired
    private PloggingServiceFacade ploggingServiceFacade;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private final int LOOP_COUNT = 10;

    @BeforeAll
    void setUp() {
        // 플로깅 데이터 초기화
        ploggingRepository.deleteAll();
        userRepository.deleteAll();

        ploggingRepository.save(
                Plogging.builder()
                        .title("플로깅")
                        .content("플로깅 같이 할 사람?")
                        .maxPeople(2L)  // 최대 2명으로 설정
                        .ploggingType(DIRECT)
                        .recruitStartDate(LocalDate.now())
                        .recruitEndDate(LocalDate.now())
                        .startTime(LocalDate.now().atStartOfDay())
                        .spendTime(60L)
                        .startLocation("서울시 중구")
                        .startLatitude(BigDecimal.valueOf(37.569477583193))
                        .startLongitude(BigDecimal.valueOf(126.97779641524))
                        .build()
        );

        // 사용자 데이터 초기화
        for (long i  = 1; i <= LOOP_COUNT; i++) {
            userRepository.save(
                    User.builder()
                            .nickname("유저" + i)
                            .email("test" + i + "@email.com")
                            .profileImageUrl("https://profile.com")
                            .profileMessage("프로필 메시지")
                            .loginType(SELF)
                            .isAlarmAllowed(true)
                            .isProfilePublic(true)
                            .role(Role.USER)
                            .fcmToken("fcmToken")
                            .build()
            );
        }

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @BeforeEach
    void setUpEach() {
        ploggingUserRepository.deleteAll();
    }

    @Test
    @DisplayName("플로깅 참여하기 API 테스트 - 동시성")
    void joinPloggingAPI() throws Exception {
        // given
        ExecutorService executorService = Executors.newFixedThreadPool(LOOP_COUNT);
        CountDownLatch latch = new CountDownLatch(LOOP_COUNT);

        StopWatch stopWatch = new StopWatch();

        // when
        stopWatch.start();

        for (long i = 1; i <= LOOP_COUNT; i++) {
            long userId = i;
            long ploggingId = 1L;

            executorService.execute(() -> {
                try {
                    mockMvc.perform(post("/ploggings/{ploggingId}", ploggingId)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .param("userId", String.valueOf(userId)))
                            .andExpect(MockMvcResultMatchers.status().isOk());
                } catch (Exception e) {
                    log.error("Error in concurrent test: ", e);
                    fail("Concurrent test failed due to exception: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        stopWatch.stop();

        executorService.shutdown();

        // then
        long participantCount = ploggingUserRepository.countActivePloggingUsersByPloggingId(1L);
        assertThat(participantCount).isEqualTo(2); // 최대 인원수 2명 체크

        log.info("참가자 수: {}", participantCount);
        log.info("반복 횟수: {}", LOOP_COUNT);
        log.info("총 소요 시간: {}", stopWatch.getTotalTimeMillis());
    }

    @Test
    @DisplayName("플로깅 참여하기 테스트 - 동시성")
    void joinPloggingConcurrent() throws Exception {
        // given
        long ploggingId = 1L;

        ExecutorService executorService = Executors.newFixedThreadPool(LOOP_COUNT);
        CountDownLatch latch = new CountDownLatch(LOOP_COUNT);

        List<String> responses = Collections.synchronizedList(new ArrayList<>());

        for (int i = 0; i < LOOP_COUNT; i++) {
            long userId = i + 1;

            executorService.execute(() -> {
                try {
                    String response = ploggingServiceFacade.joinPlogging(ploggingId, userId);
                    responses.add(response);
                } catch (Exception e) {
                    log.error("Error in concurrent test: ", e);
                    fail("Concurrent test failed due to exception: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(); // 모든 스레드가 작업을 완료할 때까지 기다림
        executorService.shutdown();

        for (String response : responses) {
            log.info("Response: {}", response);
        }

        // then
        long participantCount = ploggingUserRepository.countActivePloggingUsersByPloggingId(1L);
        assertThat(participantCount).isEqualTo(2); // 최대 인원수 2명 체크

        long successfulJoins = responses.stream()
                .filter(r -> r.equals(DIRECT_COMPLETE.getMessage()))
                .count();
        assertThat(successfulJoins).isEqualTo(participantCount);

        long failedJoins = responses.stream()
                .filter(r -> r.equals(FULL_RECRUIT.getMessage()))
                .count();
        assertThat(failedJoins).isEqualTo(LOOP_COUNT - successfulJoins);

        log.info("참가자 수: {}", participantCount);
        log.info("성공적인 참여: {}", successfulJoins);
        log.info("실패한 참여: {}", failedJoins);
    }
}