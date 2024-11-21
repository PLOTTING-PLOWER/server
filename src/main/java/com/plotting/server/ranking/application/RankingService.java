package com.plotting.server.ranking.application;

import com.plotting.server.ranking.domain.Ranking;
import com.plotting.server.ranking.exception.RankingNotFoundException;
import com.plotting.server.ranking.repository.RankingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.plotting.server.user.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RankingService {
    private final RankingRepository rankingRepository;

    public Ranking getRanking(Long userId){
        return rankingRepository.findByUserId(userId)
                .orElseThrow(() -> new RankingNotFoundException(USER_NOT_FOUND));
    }
}
