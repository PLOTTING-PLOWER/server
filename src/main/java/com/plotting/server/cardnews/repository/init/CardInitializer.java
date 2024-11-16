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

            Cardnews DATA_Cardnews1 = cardnewsRepository.findById(1L).orElseThrow();
            Cardnews DATA_Cardnews2 = cardnewsRepository.findById(2L).orElseThrow();
            Cardnews DATA_Cardnews3 = cardnewsRepository.findById(3L).orElseThrow();

            Card DUMMY_Card1 = Card.builder()
                    .cardnews(DATA_Cardnews1)
                    .cardImageUrl(s3Endpoint + "/plastic/zeroPlastic_1.png")
                    .cardImageUrl(s3Endpoint + "/plastic/zeroPlastic_2.png")
                    .cardImageUrl(s3Endpoint + "/plastic/zeroPlastic_3.png")
                    .build();

            Card DUMMY_Card2 = Card.builder()
                    .cardnews(DATA_Cardnews2)
                    .cardImageUrl(s3Endpoint + "/plogging/plogging_1.png")
                    .cardImageUrl(s3Endpoint + "/plogging/plogging_2.png")
                    .cardImageUrl(s3Endpoint + "/plogging/plogging_3.png")
                    .build();

            Card DUMMY_Card3 = Card.builder()
                    .cardnews(DATA_Cardnews3)
                    .cardImageUrl(s3Endpoint + "/plogging_info/plogging_info_1.png")
                    .build();

            cardList.add(DUMMY_Card1);
            cardList.add(DUMMY_Card2);
            cardList.add(DUMMY_Card3);

            cardRepository.saveAll(cardList);

        }
    }
}
