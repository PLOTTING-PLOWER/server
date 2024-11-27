package com.plotting.server.plogging.repository;

import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.domain.PloggingUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MyPloggingRepository extends JpaRepository<PloggingUser, Long> {

    // 특정 유저가 참여한 플로깅 데이터를 조회 (종료된 플로깅만)
    @Query("SELECT pu.plogging FROM PloggingUser pu " +
            "WHERE pu.user.id = :userId AND pu.plogging.recruitEndDate < :currentDate")
    List<Plogging> findParticipatedPloggings(@Param("userId") Long userId,
                                             @Param("currentDate") LocalDate currentDate);


    // 특정 유저가 참여한 예정된 플로깅 데이터를 조회
    @Query("SELECT pu.plogging FROM PloggingUser pu " +
            "WHERE pu.user.id = :userId AND pu.plogging.startTime > :currentDateTime")

    List<Plogging> findScheduledPloggings(@Param("userId") Long userId,
                                          @Param("currentDateTime") LocalDateTime currentDateTime);


}
