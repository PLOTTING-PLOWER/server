package com.plotting.server.alarm.domain;


import com.plotting.server.global.domain.BaseTimeEntity;
import com.plotting.server.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;

@Table(name = "alarm")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Alarm extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "reply_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "content", nullable = false)
    private String content;

    @Builder
    public Alarm(User user, String content){
        this.user = user;
        this.content = content;
    }

    public static Alarm of(User user, String content){
        return Alarm.builder()
                .user(user)
                .content(content)
                .build();
    }
}
