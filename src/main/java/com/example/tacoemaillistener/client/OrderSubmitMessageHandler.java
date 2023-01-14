package com.example.tacoemaillistener.client;

import com.example.tacoemaillistener.config.ApiConfig;
import com.example.tacoemaillistener.model.EmailOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.integration.core.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class OrderSubmitMessageHandler implements GenericHandler<EmailOrder> {

    private final RestTemplateBuilder restTemplateBuilder;
    private final ApiConfig config;

    @Override
    public Object handle(EmailOrder payload, MessageHeaders headers) {
        RestTemplate template =restTemplateBuilder
                .basicAuthentication(config.getLogin(), config.getPassword())
                .build();
        template.postForObject(config.getUrl(), payload, String.class);
        return null;
    }
}
