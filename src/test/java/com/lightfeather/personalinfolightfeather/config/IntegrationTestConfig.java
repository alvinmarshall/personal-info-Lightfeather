package com.lightfeather.personalinfolightfeather.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IntegrationTestConfig {
    @Bean
    public WireMockServer mockServer() {
        return new WireMockServer(9000);
    }
}
