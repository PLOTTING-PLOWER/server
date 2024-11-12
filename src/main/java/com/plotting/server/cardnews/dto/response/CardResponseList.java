package com.plotting.server.cardnews.dto.response;

import java.util.List;

public record CardResponseList(
        List<CardResponse> cardResponseList
) {
    public static CardResponseList from(List<CardResponse> cardResponseList) {
        return new CardResponseList(cardResponseList);
    }
}
