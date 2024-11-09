package com.plotting.server.plogging.repository;

import com.plotting.server.plogging.domain.Plogging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
}