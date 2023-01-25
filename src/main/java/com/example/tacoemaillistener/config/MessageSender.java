package com.example.tacoemaillistener.config;

import com.example.tacoemaillistener.client.GreetingRsocketClient;
import com.example.tacoemaillistener.client.IngredientRsocketClient;
import com.example.tacoemaillistener.model.Ingredient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageSender {

    @Bean
    public CommandLineRunner send(GreetingRsocketClient greeting, IngredientRsocketClient ingredient) {
        return args -> {
            greeting.sendGreeting("hello!");
            ingredient.getIngredients(Ingredient.Type.CHEESE);
        };
    }

}
