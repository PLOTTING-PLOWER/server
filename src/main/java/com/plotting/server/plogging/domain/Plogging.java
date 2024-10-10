package com.plotting.server.plogging.domain;

import com.plotting.server.plogging.domain.type.PloggingType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Table(name = "plogging")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Plogging {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "max_people", nullable = false)
    private Long maxPeople;

    @Enumerated(EnumType.STRING)
    @Column(name = "plogging_type", nullable = false)
    private PloggingType ploggingType;

    @Column(name = "start_date", nullable = false)
    private Timestamp startDate;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_date", nullable = false)
    private Timestamp endDate;

    @Column(name = "spend_time", nullable = false)
    private Long spendTime;

    @Column(name = "start_location", nullable = false)
    private String startLocation;

    @Column(name = "start_latitude",  precision = 12, scale = 6, nullable = false)
    private BigDecimal startLatitude;

    @Column(name = "start_longitude",  precision = 12, scale = 6, nullable = false)
    private BigDecimal startLongitude;

    @Column(name = "end_location")
    private String endLocation;

    @Column(name = "created_date", nullable = false)
    private Timestamp createdDate;

    @Builder
    public Plogging(String title, String content, Long maxPeople, PloggingType ploggingType,
                    Timestamp startDate, LocalDateTime startTime, Timestamp endDate,
                    Long spendTime, String startLocation, BigDecimal startLatitude,
                    BigDecimal startLongitude, String endLocation, Timestamp createdDate) {
        this.title = title;
        this.content = content;
        this.maxPeople = maxPeople;
        this.ploggingType = ploggingType;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.spendTime = spendTime;
        this.startLocation = startLocation;
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.endLocation = endLocation;
        this.createdDate = createdDate;
    }
}
