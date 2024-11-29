package com.plotting.server.alarm.repository;

import com.plotting.server.alarm.domain.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    List<Alarm> findAllByUserIdOrderByCreatedDateDesc(Long userId);
}
