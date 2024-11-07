package com.plotting.server.cardnews.dto.response;

import com.plotting.server.cardnews.domain.Cardnews;
import lombok.Builder;

@Builder
public record CardnewsResponse(
        Long cardnewsId,
        String cardnewsTitle
) {
    public static CardnewsResponse from(Cardnews cardnews) {
        return CardnewsResponse.builder()
                .cardnewsId(cardnews.getId())
                .cardnewsTitle(cardnews.getTitle())
                .build();
    }
}
