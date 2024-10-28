package com.plotting.server.cardnews.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "cardnews")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Cardnews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @OneToMany(mappedBy = "cardnews", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cards = new ArrayList<>();

    @Builder
    public Cardnews(String title, List<Card> cards) {
        this.title = title;
        this.cards = cards;
    }
}
