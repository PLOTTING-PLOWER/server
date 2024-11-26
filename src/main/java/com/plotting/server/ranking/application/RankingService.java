package com.plotting.server.ranking.application;

import com.plotting.server.ranking.domain.Ranking;
import com.plotting.server.ranking.dto.response.RankingResponse;
import com.plotting.server.ranking.exception.RankingNotFoundException;
import com.plotting.server.ranking.repository.RankingRepository;
import com.plotting.server.user.domain.User;
import com.plotting.server.user.exception.UserNotFoundException;
import com.plotting.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.plotting.server.user.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RankingService {
    private final RankingRepository rankingRepository;
    private final UserRepository userRepository;

    public Ranking getRanking(Long userId){
        return rankingRepository.findByUserId(userId)
                .orElseThrow(() -> new RankingNotFoundException(USER_NOT_FOUND));
    }

    public List<RankingResponse> getTop7Rankings(){
        return rankingRepository.findTop7Rankings();
    }

    public RankingResponse getRankingResponse(Long userId){
        return rankingRepository.findUserRanking(userId)
                .orElseGet(()-> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
                    return RankingResponse.from(user);
                });

    }
}
