package com.example.demo.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Flux<Object> getAll();
    Mono<Void> save(Object object);
    Mono<Void> deleteAll();
}
