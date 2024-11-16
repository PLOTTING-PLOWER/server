package com.plotting.server.cardnews.repository.init;

import com.plotting.server.cardnews.domain.Card;
import com.plotting.server.cardnews.domain.Cardnews;
import com.plotting.server.cardnews.repository.CardRepository;
import com.plotting.server.cardnews.repository.CardnewsRepository;
import com.plotting.server.global.util.DummyDataInit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Order(2)
@DummyDataInit
public class CardInitializer implements ApplicationRunner {

    @Value("${cloud.aws.s3.endpoint}")
    private String s3Endpoint;

    private final CardRepository cardRepository;
    private final CardnewsRepository cardnewsRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (cardRepository.count() > 0) {
            log.info("[Card]더미 데이터 존재");
        } else {

            List<Card> cardList = new ArrayList<>();

            final Cardnews CARDNEWS1 = cardnewsRepository.findById(1L).orElseThrow();
            final Cardnews CARDNEWS2 = cardnewsRepository.findById(2L).orElseThrow();
            final Cardnews CARDNEWS3 = cardnewsRepository.findById(3L).orElseThrow();

            final Card CARD1 = Card.builder()
                    .cardnews(CARDNEWS1)
                    .cardImageUrl(s3Endpoint + "/plastic/zeroPlastic_1")
                    .build();

            final Card CARD2 = Card.builder()
                    .cardnews(CARDNEWS1)
                    .cardImageUrl(s3Endpoint + "/plastic/zeroPlastic_2")
                    .build();

            final Card CARD3 = Card.builder()
                    .cardnews(CARDNEWS1)
                    .cardImageUrl(s3Endpoint + "/plastic/zeroPlastic_3")
                    .build();

            final Card CARD4 = Card.builder()
                    .cardnews(CARDNEWS2)
                    .cardImageUrl(s3Endpoint + "/plogging/plogging_1")
                    .build();

            final Card CARD5 = Card.builder()
                    .cardnews(CARDNEWS2)
                    .cardImageUrl(s3Endpoint + "/plogging/plogging_2")
                    .build();

            final Card CARD6 = Card.builder()
                    .cardnews(CARDNEWS2)
                    .cardImageUrl(s3Endpoint + "/plogging/plogging_3")
                    .build();

            final Card CARD7 = Card.builder()
                    .cardnews(CARDNEWS3)
                    .cardImageUrl(s3Endpoint + "/plogging_info/plogging_info_1")
                    .build();

            cardList.add(CARD1);
            cardList.add(CARD2);
            cardList.add(CARD3);
            cardList.add(CARD4);
            cardList.add(CARD5);
            cardList.add(CARD6);
            cardList.add(CARD7);

            cardRepository.saveAll(cardList);

        }
    }
}
