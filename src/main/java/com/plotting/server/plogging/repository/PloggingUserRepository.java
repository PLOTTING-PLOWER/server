package com.plotting.server.plogging.repository;

import com.plotting.server.plogging.domain.PloggingUser;
import com.plotting.server.plogging.dto.response.MonthlyPloggingResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PloggingUserRepository extends JpaRepository<PloggingUser, Long> {

    @Query("SELECT COUNT(pu) FROM PloggingUser pu WHERE pu.plogging.id = :ploggingId AND pu.isAssigned = true")
    Long countActivePloggingUsersByPloggingId(Long ploggingId);

    @Query("SELECT pu FROM PloggingUser pu WHERE pu.plogging.id = :ploggingId AND pu.isAssigned = true")
    List<PloggingUser> findActivePloggingUsersByPloggingId(Long ploggingId);

    boolean existsByPloggingIdAndUserId(Long ploggingId, Long userId);

    @Query(value = "SELECT GET_LOCK(:key, 3000)", nativeQuery = true)
    void getLock(String key);

    @Query(value = "SELECT RELEASE_LOCK(:key)", nativeQuery = true)
    void releaseLock(String key);

    @Query("SELECT pu FROM PloggingUser pu JOIN FETCH pu.user WHERE pu.plogging.id = :ploggingId AND pu.isAssigned = false")
    List<PloggingUser> findWaitingPloggingUserByPloggingIdWithFetch(Long ploggingId);

    @Query("SELECT new com.plotting.server.plogging.dto.response.MonthlyPloggingResponse" +
            "(YEAR(p.startTime), MONTH(p.startTime), COUNT(p), SUM(p.spendTime)) " +
            "FROM PloggingUser pu " +
            "JOIN pu.plogging p " +
            "WHERE pu.user.id = :userId " +
            "GROUP BY YEAR(p.startTime), MONTH(p.startTime)")
    List<MonthlyPloggingResponse> findMonthlyPloggingStatsByUserId(Long userId);
}