package com.plotting.server.plogging.repository;

import static com.plotting.server.plogging.domain.QPlogging.plogging;
import static com.plotting.server.plogging.domain.QPloggingStar.ploggingStar;
import static com.plotting.server.plogging.domain.QPloggingUser.ploggingUser;

import com.plotting.server.plogging.domain.type.PloggingType;
import com.plotting.server.plogging.dto.response.PloggingGetStarResponse;
import com.plotting.server.plogging.dto.response.PloggingResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PloggingRepositoryCustomImpl implements PloggingRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PloggingGetStarResponse> findAllByTitleContaining(Long userId, String title, int size, Long lastViewPloggingId) {

        return jpaQueryFactory
                .select(Projections.constructor(PloggingGetStarResponse.class,
                        plogging.id,
                        plogging.title,
                        plogging.ploggingUsers.size(),
                        plogging.maxPeople,
                        plogging.ploggingType,
                        plogging.recruitEndDate,
                        plogging.startTime,
                        plogging.spendTime,
                        plogging.startLocation,
                        ploggingStar.id.isNotNull()))   // isStar
                .from(plogging)
                .leftJoin(ploggingUser).on(plogging.id.eq(ploggingUser.plogging.id))
                .leftJoin(ploggingStar).on(plogging.id.eq(ploggingStar.plogging.id).and(ploggingStar.user.id.eq(userId)))
                .orderBy(plogging.id.desc())
                .where(isFirstPloggingPaging(lastViewPloggingId), plogging.title.contains(title))
                .groupBy(plogging.id, ploggingStar.id)
                .limit(size + 1)
                .fetch();
    }

    private BooleanExpression isFirstPloggingPaging(Long lastViewPloggingId) {
        return lastViewPloggingId == 0 ? null : plogging.id.lt(lastViewPloggingId);
    }
//
//    @Override
//    public boolean hasNext(String title, Long lastSearchId, int size) {
//        return jpaQueryFactory.selectOne()
//                .from(plogging)
//                .where(plogging.title.contains(title), plogging.id.lt(lastSearchId - size))
//                .fetchFirst() != null;
//    }

    @Override
    public List<PloggingResponse> findByFilters(String region, LocalDate startDate, LocalDate endDate, PloggingType type,
                                                Long spendTime, LocalDateTime startTime, Long maxPeople) {
        return jpaQueryFactory
                .select(Projections.constructor(PloggingResponse.class,
                        plogging.id,
                        plogging.title,
                        plogging.maxPeople,
                        plogging.ploggingType,
                        plogging.recruitEndDate,
                        plogging.startTime,
                        plogging.spendTime,
                        plogging.startLocation))
                .from(plogging)
                .where(filterByRegion(region),
                        filterByType(type),
                        filterByDate(startDate, endDate),
                        filterBySpendTime(spendTime),
                        filterByStartTime(startTime),
                        filterByMaxPeople(maxPeople))
                .groupBy(plogging.id)
                .fetch();
    }

    private BooleanExpression filterByRegion(String region) {
        if (ObjectUtils.isEmpty(region)) {
            return null;
        } else {
            return plogging.startLocation.contains(region);
        }
    }

    private BooleanExpression filterByDate(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return null;
        } else {
            return plogging.recruitStartDate.after(startDate).and(plogging.recruitEndDate.before(endDate));
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
            return plogging.startTime.after(startTime);
        }
    }

    private BooleanExpression filterByMaxPeople(Long maxPeople) {
        if (maxPeople == null) {
            return null;
        } else {
            return plogging.maxPeople.loe(maxPeople);
        }
    }
}
