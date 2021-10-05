package com.example.demo.service;

import com.example.demo.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Flux<User> getAll();
    Mono<User> getUser(int id);
    Mono<Void> save(Mono<User> user);
    Mono<User> update(Mono<User> user);
    Mono<Void> delete(int id);
}
