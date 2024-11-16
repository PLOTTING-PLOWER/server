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
            log.info("[Cardnews]데이터 존재");
        } else {

            List<Cardnews> cardnewsList = new ArrayList<>();

            final Cardnews CARDNEWS1 = Cardnews.builder()
                    .title("일회용품제로 챌린지")
                    .build();

            final Cardnews CARDNEWS2 = Cardnews.builder()
                    .title("환경보호 + 운동 = 플로깅으로 시작해요!")
                    .build();


            final Cardnews CARDNEWS3 = Cardnews.builder()
                    .title("플로깅에 대해서 알아볼까요?")
                    .build();

            cardnewsList.add(CARDNEWS1);
            cardnewsList.add(CARDNEWS2);
            cardnewsList.add(CARDNEWS3);

            cardnewsRepository.saveAll(cardnewsList);
        }
    }
}