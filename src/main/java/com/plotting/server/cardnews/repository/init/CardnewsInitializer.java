package com.plotting.server.cardnews.repository.init;

import com.plotting.server.cardnews.domain.Cardnews;
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
@Order(1)
@DummyDataInit
public class CardnewsInitializer implements ApplicationRunner {

    private final CardnewsRepository cardnewsRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (cardnewsRepository.count() > 0) {
            log.info("[User]더미 데이터 존재");
        } else {

            List<Cardnews> cardnewsList = new ArrayList<>();

            Cardnews DUMMY_Cardnews1 = Cardnews.builder()
                    .title("title1")
                    .build();

            Cardnews DUMMY_Cardnews2 = Cardnews.builder()
                    .title("title2")
                    .build();

            Cardnews DUMMY_Cardnews3 = Cardnews.builder()
                    .title("title3")
                    .build();

            Cardnews DUMMY_Cardnews4 = Cardnews.builder()
                    .title("title4")
                    .build();

            Cardnews DUMMY_Cardnews5 = Cardnews.builder()
                    .title("title5")
                    .build();

            cardnewsList.add(DUMMY_Cardnews1);
            cardnewsList.add(DUMMY_Cardnews2);
            cardnewsList.add(DUMMY_Cardnews3);
            cardnewsList.add(DUMMY_Cardnews4);
            cardnewsList.add(DUMMY_Cardnews5);

            cardnewsRepository.saveAll(cardnewsList);
        }
    }
}