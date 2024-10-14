package com.plotting.server.plogging.application;

import com.plotting.server.plogging.dto.response.PloggingDetailResponse;
import com.plotting.server.plogging.dto.response.PloggingUserListResponse;
import com.plotting.server.plogging.repository.PloggingRepository;
import com.plotting.server.plogging.repository.PloggingUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.plotting.server.plogging.fixture.PloggingFixture.PLOGGING;
import static com.plotting.server.plogging.fixture.PloggingUserFixture.PLOGGING_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PloggingServiceTest {

    @InjectMocks
    private PloggingService ploggingService;

    @Mock
    private PloggingRepository ploggingRepository;

    @Mock
    private PloggingUserRepository ploggingUserRepository;

    @Test
    @DisplayName("플로깅 상세 조회 테스트")
    void getPloggingDetailTest() {
        // given
        given(ploggingRepository.findById(anyLong()))
                .willReturn(Optional.of(PLOGGING));
        given(ploggingUserRepository.countActivePloggingUsersByPloggingId(anyLong()))
                .willReturn(5L);

        // when
        PloggingDetailResponse response = ploggingService.getPloggingDetail(PLOGGING.getId());

        // then
        assertThat(response.title()).isEqualTo(PLOGGING.getTitle());
    }

    @Test
    @DisplayName("플로깅 유저 리스트 조회 테스트")
    void getPloggingUserListTest() {
        // given
        given(ploggingUserRepository.findActivePloggingUsersByPloggingId(anyLong()))
                .willReturn(List.of(PLOGGING_USER));
        given(ploggingRepository.findMaxPeopleById(anyLong()))
                .willReturn(5L);

        // when
        PloggingUserListResponse response = ploggingService.getPloggingUserList(PLOGGING.getId());

        // then
        assertThat(response.maxPeople()).isEqualTo(5L);
    }
}