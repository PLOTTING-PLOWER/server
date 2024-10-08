package com.plotting.server.cardnews.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "card")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "card_image_url", nullable = false)
    private String cardImageUrl;

    @JoinColumn(name = "cardnews_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cardnews cardnews;

    @Builder
    public Card(String cardImageUrl, Cardnews cardnews) {
        this.cardImageUrl = cardImageUrl;
        this.cardnews = cardnews;
    }
}