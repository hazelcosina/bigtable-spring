package com.example.demo.service;

import com.example.demo.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Flux<User> getAll();
    Mono<User> getUser(long id);
    Mono<User> getUser(String firstName);
}
