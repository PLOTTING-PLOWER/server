package com.plotting.server.plogging.application;

import com.plotting.server.plogging.dto.response.GeocodeResponse;
import com.plotting.server.plogging.presentation.GeocodeClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeocodeService {

    private final GeocodeClient geocodeClient;

    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    public GeocodeResponse getGeocode(String address) {
        return geocodeClient.getGeocode(clientId, clientSecret, address);
    }
}
