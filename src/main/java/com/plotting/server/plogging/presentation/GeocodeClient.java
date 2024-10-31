package com.plotting.server.plogging.presentation;

import com.plotting.server.plogging.dto.response.GeocodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "geocode-client", url = "${geocode.api.url}")
public interface GeocodeClient {
    @GetMapping("/geocode")
    GeocodeResponse getGeocode(
            @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret,
            //맞는지 확인해야함. 의심의심!!
            @RequestParam("query") String query,
            @RequestParam(value = "filter", required = false) String filter
    );
}