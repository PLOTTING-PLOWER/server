package com.plotting.server.cardnews.application;

import com.plotting.server.cardnews.domain.Cardnews;
import com.plotting.server.cardnews.dto.response.CardResponse;
import com.plotting.server.cardnews.dto.response.CardnewsResponse;
import com.plotting.server.cardnews.dto.response.CardnewsResponseList;
import com.plotting.server.cardnews.exception.CardNewsNotFoundException;
import com.plotting.server.cardnews.repository.CardnewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.plotting.server.cardnews.exception.errorcode.CardnewsErrorCode.CARDNEWS_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardnewsService {
    private final CardnewsRepository cardnewsRepository;

    //카드뉴스 리스트 조회
    public CardnewsResponseList getCardnewsList() {
        List<CardnewsResponse> cardnewsList = cardnewsRepository.findAll().stream()
                .map(CardnewsResponse::from)
                .toList();

        return CardnewsResponseList.from(cardnewsList);
    }

    //카드뉴스 상세 조회
    public CardResponse getCard(Long cardnewsId) {
        Cardnews cardnews = cardnewsRepository.findById(cardnewsId)
                .orElseThrow(() -> new CardNewsNotFoundException(CARDNEWS_NOT_FOUND));

        return CardResponse.from(cardnews);
    }
}
