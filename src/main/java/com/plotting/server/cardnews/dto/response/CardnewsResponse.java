package com.plotting.server.cardnews.dto.response;

import com.plotting.server.cardnews.domain.Cardnews;
import lombok.Builder;

@Builder
public record CardnewsResponse(
        Long id,
        String cardnewsTitle
) {
    public static CardnewsResponse from(Cardnews cardnews) {
        return CardnewsResponse.builder()
                .id(cardnews.getId())
                .cardnewsTitle(cardnews.getTitle())
                .build();
    }
}
