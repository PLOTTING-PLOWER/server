package com.plotting.server.ranking.application;

import com.plotting.server.ranking.domain.Ranking;
import com.plotting.server.ranking.exception.RankingNotFoundException;
import com.plotting.server.ranking.repository.RankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.plotting.server.user.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RankingService {
    private final RankingRepository rankingRepository;

    public Ranking getRanking(Long userId){
        return rankingRepository.findById(userId)
                .orElseThrow(() -> new RankingNotFoundException(USER_NOT_FOUND));
    }
}
