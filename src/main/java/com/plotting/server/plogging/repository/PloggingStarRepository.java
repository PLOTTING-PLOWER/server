package com.plotting.server.plogging.repository;

import com.plotting.server.plogging.domain.PloggingStar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PloggingStarRepository extends JpaRepository<PloggingStar, Long> {
}
