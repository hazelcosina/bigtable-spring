package com.example.demo.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BigTableRepository {
    Flux<Object> getAll();
    Mono<Void> save(Object object);
    Mono<Void> deleteAll();
}
