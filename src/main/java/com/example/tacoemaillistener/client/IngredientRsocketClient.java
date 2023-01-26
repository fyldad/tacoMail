package com.example.tacoemaillistener.client;

import com.example.tacoemaillistener.model.Ingredient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

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

    public void sendIngredient(Ingredient ingredient) {
        tcp.route("ingredient")
                .data(ingredient)
                .send()
                .subscribe();
        log.info("ingredient sent to space: {}", ingredient);
    }

    public void getIngredientsById(Flux<String> ingredientIdFlux) {
        tcp.route("ingredientFlux")
                .data(ingredientIdFlux)
                .retrieveFlux(Ingredient.class)
                .subscribe(ingredient -> log.info("received ingredient from id: {}", ingredient));
    }

}
