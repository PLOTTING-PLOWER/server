package com.plotting.server.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "user_star")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UserStar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "star_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User starUser;

    @Builder
    public UserStar(User user, User starUser) {
        this.user = user;
        this.starUser = starUser;
    }

    public static UserStar of(User user, User starUser) {
        return UserStar.builder()
                .user(user)
                .starUser(starUser)
                .build();
    }
}