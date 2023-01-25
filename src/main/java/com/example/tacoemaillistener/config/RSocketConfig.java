package com.example.tacoemaillistener.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;

@Configuration
public class RSocketConfig {

    @Bean
    public RSocketRequester requester(RSocketRequester.Builder requestBuilder) {
        return requestBuilder.tcp("localhost", 8444);
    }

}
