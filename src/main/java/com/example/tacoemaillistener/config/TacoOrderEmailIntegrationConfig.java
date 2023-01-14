package com.example.tacoemaillistener.config;

import com.example.tacoemaillistener.client.OrderSubmitMessageHandler;
import com.example.tacoemaillistener.converter.EmailToOrderTransformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.mail.dsl.Mail;

@Configuration
public class TacoOrderEmailIntegrationConfig {

    @Bean
    public IntegrationFlow tacoOrderEmailFlow(EmailConfig config, EmailToOrderTransformer transformer,
                                              OrderSubmitMessageHandler handler) {
        return IntegrationFlow
                .from(Mail.imapInboundAdapter(config.getImapUrl()),
                        e -> e.poller(Pollers.fixedDelay(config.getPollRate())))
                .transform(transformer)
                .handle(handler)
                .get();
    }

}
