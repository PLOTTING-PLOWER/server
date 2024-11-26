package com.plotting.server.ranking.application;

import com.plotting.server.plogging.application.MyPloggingService;
import com.plotting.server.ranking.domain.Ranking;
import com.plotting.server.ranking.dto.response.UpdateRankingListResponse;
import com.plotting.server.ranking.repository.RankingRepository;
import com.plotting.server.user.application.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RankingUpdateService {

    private final RankingRepository rankingRepository;
    private final MyPloggingService myPloggingService;
    private final UserService userService;

//    @Scheduled(cron = "0 0 * * * *") // 매 정각 실행
//    @Scheduled(fixedRate = 60000) // 1분 마다 실행 (1분 = 60,000밀리초)
    @Scheduled(fixedRate = 3600000) // 1시간 마다 실행 (1시간 = 3,600,000밀리초)
    @Transactional
    public void updateMonthlyRanking(){
        log.info("Starting updateMonthlyRanking at: "+ LocalDateTime.now());
        try {
            // 기존 랭킹 삭제(전체 삭제)
            rankingRepository.deleteAll();
            rankingRepository.flush();
            log.info("All existing rankings deleted.");

            // 새로운 랭킹 데이터 계산
            UpdateRankingListResponse updateRankingListResponse = myPloggingService.getMonthlyPloggingStats();

            AtomicInteger rankCounter = new AtomicInteger(1);
            List<Ranking> newRankings = updateRankingListResponse.rankingListResponse()
                    .stream()
                    .map(response -> Ranking.create(
                            userService.getUser(response.userId()),
                            Long.valueOf(rankCounter.getAndIncrement()),
                            response.totalHours(),
                            response.totalCount()
                    )).toList();

            log.info("New Rankings to Save: " + newRankings.size());
            newRankings.forEach(ranking -> log.info("Saving Ranking: " + ranking));

            rankingRepository.saveAll(newRankings);
        }catch(Exception e){
            log.error(e.getMessage());
        }
        log.info("Completed updateMonthlyRanking at: " + LocalDateTime.now());
    }
}
