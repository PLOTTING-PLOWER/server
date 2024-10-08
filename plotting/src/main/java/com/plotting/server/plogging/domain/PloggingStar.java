package com.plotting.server.plogging.domain;

import com.plotting.server.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "plogging_star")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PloggingStar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "plogging_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Plogging plogging;

    @Builder
    public PloggingStar(User user, Plogging plogging) {
        this.user = user;
        this.plogging = plogging;
    }
}