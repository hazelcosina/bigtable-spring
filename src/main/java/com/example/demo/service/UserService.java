package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.UserPayload;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Flux<User> getAll();
    Mono<Void> save(UserPayload object);
    Mono<Void> deleteAll();
}
