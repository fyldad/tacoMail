package com.example.tacoemaillistener.client;

import com.example.tacoemaillistener.model.Ingredient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class IngredientRsocketClient {

    private final RSocketRequester tcp;

    public void getIngredients(Ingredient.Type type) {
        tcp.route("ingredient/{type}", type)
                .retrieveFlux(Ingredient.class)
                .subscribe(response -> log.info("received a response: {}", response));
    }



}
