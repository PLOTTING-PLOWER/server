package com.plotting.server.plogging.repository;

import com.plotting.server.plogging.domain.Plogging;
import com.plotting.server.plogging.domain.PloggingStar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PloggingStarRepository extends JpaRepository<PloggingStar, Long> {

    @Query("SELECT ps.plogging FROM PloggingStar ps WHERE ps.user.id = :userId")
    List<Plogging> findPloggingByUserId(Long userId);

    Optional<PloggingStar> findByUserIdAndPloggingId(Long userId, Long ploggingId);
}
