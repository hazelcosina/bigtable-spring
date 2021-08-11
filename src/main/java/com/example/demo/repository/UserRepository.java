package com.example.demo.repository;

import com.example.demo.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Flux<User> getAll();
    Mono<Void> save(User user);
    Mono<Void> deleteAll();
}
