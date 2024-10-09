package com.plotting.server.alarm.domain;


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
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "reply_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "alarm_date", nullable = false)
    private LocalDate alarmDate;

    @Column(name = "content", nullable = false)
    private String content;

    @Builder
    public Alarm(User user, LocalDate alarmDate, String content){
        this.user = user;
        this.alarmDate = alarmDate;
        this.content = content;
    }
}
