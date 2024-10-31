package com.plotting.server.plogging.application;

import com.plotting.server.plogging.dto.response.GeocodeResponse;
import com.plotting.server.plogging.presentation.GeocodeClient;
import com.plotting.server.plogging.repository.PloggingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeocodeService {

    private final PloggingService ploggingService;
    private final PloggingRepository ploggingRepository;
    private final GeocodeClient geocodeClient;

    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    public GeocodeResponse getGeocode() {
        //외부 API로 위도, 경도 받아오기 -> 파사드 패턴 이용해서 (외부 서버 갔다오는 동안) 리소스(connection) 누수 : connection-pool
        GeocodeResponse geocode = geocodeClient.getGeocode(clientId, clientSecret);
        //비지니스 로직 나오고
        return geocode;
    }
}
