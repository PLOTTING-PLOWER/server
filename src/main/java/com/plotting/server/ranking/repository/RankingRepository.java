package com.plotting.server.ranking.repository;

import com.plotting.server.ranking.domain.Ranking;
import com.plotting.server.ranking.dto.response.RankingResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long> {

    Optional<Ranking> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    @Modifying
    @Query("DELETE FROM Ranking")
    void deleteAllRankings();

    @Query("SELECT new com.plotting.server.ranking.dto.response.RankingResponse(" +
            "r.user.id, r.user.nickname, r.user.profileImageUrl, r.hourRank, r.totalHours, r.totalCount) " +
            "FROM Ranking r " +
            "JOIN r.user u " +
            "ORDER BY r.hourRank ASC " +
            "LIMIT 7")
    List<RankingResponse> findTop7Rankings();

    @Query("SELECT new com.plotting.server.ranking.dto.response.RankingResponse(" +
            "r.user.id, u.nickname, u.profileImageUrl, r.hourRank, r.totalHours, r.totalCount) " +
            "FROM Ranking r " +
            "JOIN r.user u " +
            "WHERE u.id =:userId ")
    Optional<RankingResponse> findUserRanking(Long userId);
}
