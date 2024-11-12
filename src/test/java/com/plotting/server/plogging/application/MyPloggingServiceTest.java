package com.plotting.server.plogging.application;

import com.plotting.server.plogging.dto.request.PloggingUpdateRequest;
import com.plotting.server.plogging.dto.response.MyPloggingCreatedListResponse;
import com.plotting.server.plogging.repository.PloggingRepository;
import com.plotting.server.plogging.repository.PloggingUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.plotting.server.plogging.fixture.PloggingFixture.PLOGGING;
import static com.plotting.server.plogging.fixture.PloggingUserFixture.PLOGGING_USER2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MyPloggingServiceTest {

    @InjectMocks
    private MyPloggingService myPloggingService;

    @Mock
    private PloggingService ploggingService;

    @Mock
    private PloggingRepository ploggingRepository;

    @Mock
    private PloggingUserRepository ploggingUserRepository;

    @Test
    @DisplayName("내가 만든 플로깅 리스트 조회 테스트")
    void getMyPloggingCreatedListTest() {
        // given
        given(ploggingRepository.findAllByUserId(anyLong()))
                .willReturn(List.of(PLOGGING));

        // when
        MyPloggingCreatedListResponse response = myPloggingService.getMyPloggingCreatedList(1L);

        // then
        assertThat(response.myPloggings().get(0).title()).isEqualTo(PLOGGING.getTitle());
    }

    @Test
    @DisplayName("플로깅 수정 테스트")
    void updatePloggingTest() {
        // given
        PloggingUpdateRequest request = new PloggingUpdateRequest(
                "플로깅 수정 테스트",
                "플로깅 수정 테스트",
                5L,
                LocalDate.now(),
                LocalDate.now(),
                LocalDateTime.now(),
                60L
        );

        given(ploggingService.getPlogging(anyLong()))
                .willReturn(PLOGGING);

        // when
        myPloggingService.updatePlogging(PLOGGING.getId(), request);

        // then
        assertThat(PLOGGING.getTitle()).isEqualTo("플로깅 수정 테스트");
    }

    @Test
    @DisplayName("플로깅 삭제 테스트")
    void deleteMyPloggingTest() {
        // when
        myPloggingService.deleteMyPlogging(PLOGGING.getId());

        // then
        verify(ploggingRepository).deleteById(PLOGGING.getId());
    }

    @Test
    @DisplayName("승인 대기 멤버 조회 테스트")
    void getMyPloggingUserTest() {
        // given
        given(ploggingUserRepository.findWaitingPloggingUserByPloggingIdWithFetch(anyLong()))
                .willReturn(List.of());

        // when
        myPloggingService.getMyPloggingUser(PLOGGING.getId());

        // then
        verify(ploggingUserRepository).findWaitingPloggingUserByPloggingIdWithFetch(PLOGGING.getId());
    }

    @Test
    @DisplayName("플로깅 참가 승인 테스트")
    void updatePloggingUserTest() {
        // given
        given(ploggingService.getPlogging(anyLong()))
                .willReturn(PLOGGING);
        given(ploggingUserRepository.findById(anyLong()))
                .willReturn(Optional.of(PLOGGING_USER2));

        // when
        String message = myPloggingService.updatePloggingUser(PLOGGING.getId(), PLOGGING_USER2.getId());

        // then
        assertThat(message).isEqualTo("승인되었습니다.");
    }

    @Test
    @DisplayName("플로깅 참가 거절 테스트")
    void deletePloggingUserTest() {
        // when
        myPloggingService.deletePloggingUser(PLOGGING_USER2.getId());

        // then
        verify(ploggingUserRepository).deleteById(PLOGGING_USER2.getId());
    }
}
