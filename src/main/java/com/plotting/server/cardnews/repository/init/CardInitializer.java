package com.plotting.server.cardnews.repository.init;

import com.plotting.server.cardnews.domain.Card;
import com.plotting.server.cardnews.domain.Cardnews;
import com.plotting.server.cardnews.repository.CardRepository;
import com.plotting.server.cardnews.repository.CardnewsRepository;
import com.plotting.server.global.util.DummyDataInit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final CardRepository cardRepository;
    private final CardnewsRepository cardnewsRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (cardRepository.count() > 0) {
            log.info("[User]더미 데이터 존재");
        } else {

            List<Card> cardList = new ArrayList<>();

            Cardnews DUMMY_Cardnews1 = cardnewsRepository.findById(1L).orElseThrow();
            Cardnews DUMMY_Cardnews2 = cardnewsRepository.findById(2L).orElseThrow();
            Cardnews DUMMY_Cardnews3 = cardnewsRepository.findById(3L).orElseThrow();
            Cardnews DUMMY_Cardnews4 = cardnewsRepository.findById(4L).orElseThrow();
            Cardnews DUMMY_Cardnews5 = cardnewsRepository.findById(5L).orElseThrow();

            Card DUMMY_Card1 = Card.builder()
                    .cardnews(DUMMY_Cardnews1)
                    .cardImageUrl("cardImage1.png")
                    .build();

            Card DUMMY_Card2 = Card.builder()
                    .cardnews(DUMMY_Cardnews2)
                    .cardImageUrl("cardImage2.png")
                    .build();

            Card DUMMY_Card3 = Card.builder()
                    .cardnews(DUMMY_Cardnews3)
                    .cardImageUrl("cardImage3.png")
                    .build();

            Card DUMMY_Card4 = Card.builder()
                    .cardnews(DUMMY_Cardnews4)
                    .cardImageUrl("cardImage4.png")
                    .build();

            Card DUMMY_Card5 = Card.builder()
                    .cardnews(DUMMY_Cardnews5)
                    .cardImageUrl("cardImage5.png")
                    .build();
            cardList.add(DUMMY_Card1);
            cardList.add(DUMMY_Card2);
            cardList.add(DUMMY_Card3);
            cardList.add(DUMMY_Card4);
            cardList.add(DUMMY_Card5);

            cardRepository.saveAll(cardList);

        }
    }
}
