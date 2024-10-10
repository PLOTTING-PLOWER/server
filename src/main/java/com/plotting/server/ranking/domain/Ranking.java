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

    @Builder
    public Ranking(User user, Long hourRank){
        this.user = user;
        this.hourRank = hourRank;
    }
}
