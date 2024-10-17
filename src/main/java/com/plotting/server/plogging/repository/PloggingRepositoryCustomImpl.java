package com.plotting.server.plogging.repository;

import com.plotting.server.plogging.domain.type.PloggingType;
import com.plotting.server.plogging.dto.response.PloggingResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.plotting.server.plogging.domain.QPlogging.plogging;

@Repository
@RequiredArgsConstructor
public class PloggingRepositoryCustomImpl implements PloggingRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PloggingResponse> findByFilters(List<String> region, LocalDate startDate, LocalDate endDate, PloggingType type,
                                                Long spendTime, LocalDateTime startTime, String maxParticipants) {


        return jpaQueryFactory.select(Projections.constructor(PloggingResponse.class,
                plogging.title,
                plogging.maxPeople,
                plogging.ploggingType,
                plogging.recruitEndDate,
                plogging.startTime,
                plogging.spendTime))
                .from(plogging)
                .where(filterByRegion(region),
                        filterByDate(startDate, endDate),
                        filterByType(type),
                        filterBySpendTime(spendTime),
                        filterByStartTime(startTime),
                        filterByMaxParticipants(maxParticipants))
                .groupBy(plogging.id)
                .fetch();
        }

     private BooleanExpression filterByRegion(List<String> region) {
        if (region == null || region.isEmpty()) {
            return null;
        } else {
            return plogging.startLocation.in(region);
        }
    }

    private BooleanExpression filterByDate(LocalDate startDate, LocalDate endDate) {
        if (startDate == null && endDate == null) {
            return null;
        }else if (startDate != null && endDate != null) {
            return plogging.startTime.between(startDate.atStartOfDay(), endDate.atStartOfDay());
        } else if (startDate != null) {
            return plogging.startTime.goe(startDate.atStartOfDay());
        } else {
            return plogging.startTime.loe(endDate.atStartOfDay());
        }
    }

    private BooleanExpression filterByType(PloggingType type) {
        if (type == null) {
            return null;
        } else {
            return plogging.ploggingType.eq(type);
        }
    }

    private BooleanExpression filterBySpendTime(Long spendTime) {
        if (spendTime == null) {
            return null;
        } else {
            return plogging.spendTime.loe(spendTime);
        }
    }

    private BooleanExpression filterByStartTime(LocalDateTime startTime) {
        if (startTime == null) {
            return null;
        } else {
            return plogging.startTime.goe(startTime);
        }
    }

    private BooleanExpression filterByMaxParticipants(String maxParticipants) {
        if (maxParticipants == null) {
            return null;
        } else {
            return plogging.maxPeople.loe(Long.parseLong(maxParticipants));
        }
    }
}
