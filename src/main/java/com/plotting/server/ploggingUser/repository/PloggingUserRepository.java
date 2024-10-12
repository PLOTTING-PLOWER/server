package com.plotting.server.ploggingUser.repository;

import com.plotting.server.ploggingUser.domain.PloggingUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PloggingUserRepository extends JpaRepository<PloggingUser, Long> {

    @Query("SELECT COUNT(pu) FROM PloggingUser pu WHERE pu.plogging.id = :ploggingId AND pu.isAssigned = true")
    Long countActivePloggingUsersByPloggingId(@Param("ploggingId") Long ploggingId);
}
