package com.plotting.server.cardnews.dto.response;

import com.plotting.server.cardnews.domain.Card;
import com.plotting.server.cardnews.domain.Cardnews;
import lombok.Builder;

import java.util.List;

@Builder
public record CardResponse(
        Long cardnewsId,
        String cardTitle,
        List<String> cardUrls
) {
    public static CardResponse from(Cardnews cardnews) {
        return CardResponse.builder()
                .cardnewsId(cardnews.getId())
                .cardTitle(cardnews.getTitle())
                .cardUrls(cardnews.getCards().stream()
                        .map(Card::getCardImageUrl)
                        .toList())
                .build();
    }
}
