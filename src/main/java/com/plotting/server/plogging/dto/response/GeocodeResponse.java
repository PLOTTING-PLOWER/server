package com.plotting.server.plogging.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GeocodeResponse(
        String status,
        Meta meta,
        List<Address> addresses
) {
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Meta(
            int totalCount,
            int page,
            int count
    ) {
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Address(
            String roadAddress, // 도로명 주소
            String x, // 경도
            String y // 위도
    ) {
    }
}
