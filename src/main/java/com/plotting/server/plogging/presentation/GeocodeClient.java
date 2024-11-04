package com.plotting.server.plogging.presentation;

import com.plotting.server.plogging.dto.response.GeocodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "geocode-client", url = "${geocode.api.url}")
public interface GeocodeClient {
    @GetMapping("/geocode")
    GeocodeResponse getGeocode(
            @RequestHeader("x-ncp-apigw-api-key-id") String clientId,
            @RequestHeader("x-ncp-apigw-api-key") String clientSecret,
            @RequestParam("query") String address  // 도로명 주소
    );
}
