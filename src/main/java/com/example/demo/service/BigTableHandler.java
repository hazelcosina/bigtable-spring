package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class BigTableHandler {

    private final BigTableService bigTableService;

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ServerResponse.ok()
                .body(bigTableService.getAll(), Object.class);
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<Object> requestMono = request.bodyToMono(Object.class);
        Mono<Object> mapped = requestMono.flatMap(object -> Mono.just(bigTableService.save(object)));

        return ServerResponse
                .created(URI.create("/"))
                .body(mapped, Void.class)
                .onErrorResume(Mono::error);
    }
    public Mono<ServerResponse> deleteAll(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(bigTableService.deleteAll(), Object.class);
    }
}
