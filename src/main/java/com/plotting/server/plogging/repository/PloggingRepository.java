package com.plotting.server.plogging.repository;

import com.plotting.server.plogging.domain.Plogging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PloggingRepository extends JpaRepository<Plogging, Long> {

    Optional<Plogging> findById(Long ploggingId);
}