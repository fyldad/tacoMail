package com.example.tacoemaillistener.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "taco-cloud.api")
public class ApiConfig {
    private String url;
    private String login;
    private String password;
}
