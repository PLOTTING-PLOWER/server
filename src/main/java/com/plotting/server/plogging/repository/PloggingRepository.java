package com.plotting.server.plogging.repository;

import com.plotting.server.plogging.domain.Plogging;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PloggingRepository extends JpaRepository<Plogging, Long>, PloggingRepositoryCustom {

    Optional<Plogging> findById(Long ploggingId);

    @Query("SELECT p.maxPeople FROM Plogging p WHERE p.id = :ploggingId")
    Long findMaxPeopleById(Long ploggingId);
}