package com.plotting.server.cardnews.repository;

import com.plotting.server.cardnews.domain.Cardnews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardnewsRepository extends JpaRepository<Cardnews, Long> {
    List<Cardnews> findAll();
}
