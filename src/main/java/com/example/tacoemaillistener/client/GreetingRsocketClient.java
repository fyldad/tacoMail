package com.example.tacoemaillistener.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GreetingRsocketClient {

    private final RSocketRequester tcp;

    public void sendGreeting(String data) {
        tcp.route("greeting")
                .data(data)
                .retrieveMono(String.class)
                .subscribe(response -> log.info("received a response: {}", response));
    }



}
