package com.plotting.server.global.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = {"com.plotting.server.plogging"})
public class FeignConfig {
}
