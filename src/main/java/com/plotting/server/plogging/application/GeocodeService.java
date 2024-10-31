package com.plotting.server.plogging.application;

import com.plotting.server.plogging.repository.PloggingRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GeocodeService {
    private final PloggingService ploggingService;
    private final PloggingRepository ploggingRepository;

    public void getGeocode() {
        //외부 API로 위도, 경도 받아오기 -> 파사드 패턴 이용해서 (외부 서버 갔다오는 동안) 리소스(connection) 누수 : connection-pool
        //api가 먼저 나오고
        //비지니스 로직 나오고
    }
}
