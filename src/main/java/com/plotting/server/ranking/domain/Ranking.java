package com.plotting.server.ranking.domain;


import com.plotting.server.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "ranking")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "user_id")
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "hour_rank", nullable = false)
    private Long hourRank;

    @Column(name="total_hours", nullable=false)
    private Long totalHours;    // 총 참여 시간

    @Column(name="total_count", nullable = false)
    private Long totalCount;   // 총 참여 횟수

    @Builder
    public Ranking(User user, Long hourRank, Long totalHours, Long totalCount) {
        this.user = user;
        this.hourRank = hourRank;
        this.totalHours = totalHours;
        this.totalCount = totalCount;
    }

}
