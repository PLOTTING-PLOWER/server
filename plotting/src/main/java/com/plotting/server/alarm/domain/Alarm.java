package com.plotting.server.alarm.domain;


import com.plotting.server.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

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
    private Timestamp alarmDate;

    @Column(name = "content", nullable = false)
    private String content;

    @Builder
    public Alarm(User user, Timestamp alarmDate,String content ){
        this.user =user;
        this.alarmDate = alarmDate;
        this.content = content;
    }
}
