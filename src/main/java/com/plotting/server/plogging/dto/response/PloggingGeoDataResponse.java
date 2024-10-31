package com.plotting.server.plogging.dto.response;

import lombok.Builder;

public record PloggingGeoDataResponse(
    String latitude,
    String longitude,
    String startLocation,
    String endLocation
)
{

    @Builder
    public static PloggingGeoDataResponse of(String latitude, String longitude, String startLocation, String endLocation) {
        return PloggingGeoDataResponse.builder()
            .latitude(latitude)
            .longitude(longitude)
            .startLocation(startLocation)
            .endLocation(endLocation)
            .build();
    }
}
