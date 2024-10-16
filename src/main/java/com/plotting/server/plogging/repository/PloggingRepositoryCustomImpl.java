package com.plotting.server.plogging.repository;

import com.plotting.server.plogging.domain.QPlogging;
import com.plotting.server.plogging.domain.type.PloggingType;
import com.plotting.server.plogging.dto.response.PloggingResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class PloggingRepositoryCustomImpl implements PloggingRepositoryCustom{

    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<PloggingResponse> findByFilters(String region, LocalDate startDate, LocalDate endDate, PloggingType type,
                                                Long spendTime, LocalDateTime startTime, String maxParticipants) {
        QPlogging plogging = QPlogging.plogging;

        var query = queryFactory.selectFrom(plogging)
                .where(plogging.isNotNull());

        if (region != null && !region.isEmpty()) {
            query = query.where(plogging.startLocation.eq(region));
        }
        if (startDate != null && endDate != null) {
            query = query.where(plogging.recruitStartDate.between(startDate, endDate));
        }
        if (type != null) {
            query = query.where(plogging.ploggingType.eq(type));
        }
        if (spendTime != null  && !spendTime.equals("All")) {
            query = query.where(plogging.spendTime.loe(spendTime));
        }
        if (startTime != null && !startTime.equals("All")) {
            query = query.where(plogging.startTime.goe(startTime));
        }
        if (maxParticipants != null && !maxParticipants.equals("All")) {
            Long maxPeopleNum = Long.parseLong(maxParticipants);
            query = query.where(plogging.maxPeople.loe(maxPeopleNum));
        }

//        return query.fetch().stream()
//                .map(PloggingResponse::of)
//                .collect(Collectors.toList());
    }
}
