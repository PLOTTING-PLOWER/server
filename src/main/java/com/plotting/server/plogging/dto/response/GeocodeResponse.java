package com.plotting.server.plogging.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GeocodeResponse(
        String status,
        Meta meta,
        List<Address> addresses
) {

    public BigDecimal getLatitude() {
        return addresses.get(0).y();
    }

    public BigDecimal getLongitude() {
        return addresses.get(0).x();
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Meta(
            int totalCount,
            int page,
            int count
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Address(
            String roadAddress, // 도로명 주소
            BigDecimal x, // 경도
            BigDecimal y // 위도
    ) {
    }
}
