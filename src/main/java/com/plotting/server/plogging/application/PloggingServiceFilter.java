package com.plotting.server.plogging.application;

import com.plotting.server.plogging.repository.PloggingRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PloggingServiceFilter {
    private final JPAQueryFactory jpaQueryFactory;
    private final PloggingRepository ploggingRepository;

    
//    region=SEOUL, ALL
//            minParticipants=15
//    startDate = 2024-10-07
//    endDate = 2024-10-10
//    startTime=hh
//            dueTime=mm
//    type=DIRECT or APPROVAL or ALL
}
