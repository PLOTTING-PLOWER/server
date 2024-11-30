package com.plotting.server.plogging.domain;

import com.plotting.server.global.domain.BaseTimeEntity;
import com.plotting.server.plogging.domain.type.PloggingType;
import com.plotting.server.plogging.dto.request.PloggingUpdateRequest;
import com.plotting.server.plogging.exception.PloggingNotFoundException;
import com.plotting.server.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.plotting.server.plogging.exception.errorcode.PloggingErrorCode.PLOGGING_NOT_FOUND;

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

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "start_location", nullable = false)
    private String startLocation;

    @Column(name = "start_latitude", precision = 12, scale = 6, nullable = false)
    private BigDecimal startLatitude;

    @Column(name = "start_longitude", precision = 12, scale = 6, nullable = false)
    private BigDecimal startLongitude;

    @Column(name = "end_location")
    private String endLocation;

    @OneToMany(mappedBy = "plogging", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PloggingUser> ploggingUsers = new ArrayList<>();

    @Builder
    public Plogging(User user, String title, String content, Long maxPeople, PloggingType ploggingType,
                    LocalDate recruitStartDate, LocalDate recruitEndDate, LocalDateTime startTime, Long spendTime,
                    LocalDateTime endTime, String startLocation, BigDecimal startLatitude, BigDecimal startLongitude, String endLocation) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.maxPeople = maxPeople;
        this.ploggingType = ploggingType;
        this.recruitStartDate = recruitStartDate;
        this.recruitEndDate = recruitEndDate;
        this.startTime = startTime;
        this.spendTime = spendTime;
        this.endTime = endTime;
        this.startLocation = startLocation;
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.endLocation = endLocation;
    }

    public void update(PloggingUpdateRequest request) {
        this.title = request.title();
        this.content = request.content();
        this.maxPeople = request.maxPeople();
        this.recruitStartDate = request.recruitStartDate();
        this.recruitEndDate = request.recruitEndDate();
        this.startTime = request.startTime();
        this.spendTime = request.spendTime();
        calculateEndTime();
    }

    public void calculateEndTime() {
        if (this.startTime != null && this.spendTime != null) {
            this.endTime = this.startTime.plusMinutes(this.spendTime);
        } else {
            throw new PloggingNotFoundException(PLOGGING_NOT_FOUND);
        }
    }
}
