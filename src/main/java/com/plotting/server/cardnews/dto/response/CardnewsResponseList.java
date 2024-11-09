package com.plotting.server.cardnews.dto.response;

import java.util.List;

public record CardnewsResponseList(
        List<CardnewsResponse> cardnewsResponseList
) {
    public static CardnewsResponseList from(List<CardnewsResponse> cardnewsResponseList) {
        return new CardnewsResponseList(cardnewsResponseList);
    }
}
