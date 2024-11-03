package com.plotting.server.plogging.domain;

import com.plotting.server.global.domain.BaseTimeEntity;
import com.plotting.server.plogging.domain.type.PloggingType;
import com.plotting.server.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "plogging")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Plogging extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

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
    private LocalDate recruitStartDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate recruitEndDate;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

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

    @Builder
    public Plogging(User user, String title, String content, Long maxPeople, PloggingType ploggingType,
                    LocalDate recruitStartDate, LocalDate recruitEndDate, LocalDateTime startTime, Long spendTime,
                    String startLocation, BigDecimal startLatitude, BigDecimal startLongitude, String endLocation) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.maxPeople = maxPeople;
        this.ploggingType = ploggingType;
        this.recruitStartDate = recruitStartDate;
        this.recruitEndDate = recruitEndDate;
        this.startTime = startTime;
        this.spendTime = spendTime;
        this.startLocation = startLocation;
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.endLocation = endLocation;
    }
}
