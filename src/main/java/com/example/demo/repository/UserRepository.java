package com.example.demo.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Flux<Object> getAll();
    Mono<Void> save(Object object);
    Mono<Void> deleteAll();
}
