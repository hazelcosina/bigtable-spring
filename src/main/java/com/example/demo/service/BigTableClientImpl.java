package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BigTableClientImpl implements BigTableClientService {

    private final WebClient webClient;

    @Override
    public Flux<Object> getAll() {
        System.out.println("sds");
         return webClient.get().uri("/api/applications").accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Object.class);
    }

    @Override
    public Mono<Void> save(Object object) {
        return webClient.post().uri("/api/applications",object).accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Void.class);
    }

    @Override
    public Mono<Void> deleteAll() {
        return webClient.delete().uri("/api/applications").accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
