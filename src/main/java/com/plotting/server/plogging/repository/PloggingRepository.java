package com.plotting.server.plogging.repository;

import com.plotting.server.plogging.domain.Plogging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PloggingRepository extends JpaRepository<Plogging, Long> {
}
