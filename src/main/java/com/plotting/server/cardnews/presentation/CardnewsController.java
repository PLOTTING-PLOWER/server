package com.plotting.server.cardnews.presentation;

import com.plotting.server.cardnews.application.CardnewsService;
import com.plotting.server.cardnews.dto.response.CardResponse;
import com.plotting.server.cardnews.dto.response.CardnewsResponseList;
import com.plotting.server.global.dto.ResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Cardnews", description = "카드뉴스 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cardnews")
public class CardnewsController {
    private final CardnewsService cardnewsService;

    @Operation(summary = "카드뉴스 리스트 조회", description = "카드뉴스 리스트 조회 화면입니다.")
    @GetMapping
    public ResponseEntity<ResponseTemplate<?>> getCardnews() {

        CardnewsResponseList response = cardnewsService.getCardnewsList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "카드뉴스 상세 조회", description = "카드뉴스 상세 조회 화면입니다.")
    @GetMapping("/{cardnewsId}")
    public ResponseEntity<ResponseTemplate<?>> getCard(
            @PathVariable Long cardnewsId
    ) {

        CardResponse response = cardnewsService.getCard(cardnewsId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }
}
