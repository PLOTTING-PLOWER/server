package com.plotting.server.plogging.repository;

import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.dto.response.PloggingTimeResponse;
import com.plotting.server.ranking.dto.response.UpdateRankingResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PloggingRepository extends JpaRepository<Plogging, Long>, PloggingRepositoryCustom {

    Optional<Plogging> findById(Long ploggingId);

    @Query("SELECT p " +
            "FROM Plogging p LEFT OUTER JOIN PloggingStar s ON p.id = s.plogging.id " +
            "GROUP BY p.id " +
            "ORDER BY COUNT(s.id) DESC " +
            "LIMIT 3")
    List<Plogging> findTop3Ploggings();

    @Query("SELECT p.maxPeople FROM Plogging p WHERE p.id = :ploggingId")
    Long findMaxPeopleById(Long ploggingId);

    List<Plogging> findAllByUserId(Long userId);

    List<Plogging> findByTitleContaining(String title); //검색 구현
    @Query("SELECT new com.plotting.server.plogging.dto.response.PloggingTimeResponse( p.spendTime, p.startTime ) " +
            "FROM PloggingUser pu " +
            "JOIN pu.plogging p " +
            "WHERE pu.user.id = :userId AND pu.isAssigned = true")
    List<PloggingTimeResponse> findAllPloggingTimeByUserId(Long userId);

    @Query("SELECT new com.plotting.server.ranking.dto.response.UpdateRankingResponse( pu.user.id, SUM(p.spendTime), COUNT(pu)) " +
            "FROM PloggingUser pu " +
            "JOIN pu.plogging p " +
            "WHERE p.startTime >= :startOfMonth AND p.startTime < :startOfNextMonth " +
            "AND p.endTime <= :now " +
            "AND pu.isAssigned = true " +
            "AND p.user.role = 'USER' " +
            "GROUP BY pu.user.id " +
            "ORDER BY SUM(p.spendTime) DESC, COUNT(pu) DESC")
    List<UpdateRankingResponse> findMonthlyPloggingTimeByUserId(LocalDateTime startOfMonth, LocalDateTime startOfNextMonth, LocalDateTime now);
}