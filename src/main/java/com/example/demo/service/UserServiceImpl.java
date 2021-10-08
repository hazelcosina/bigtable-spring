package com.example.demo.service;

import com.example.demo.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final WebClient webClient;

    @Override
    public Flux<User> getAll() {
        return webClient
                .get()
                .uri("/user")
                .retrieve()
                .bodyToFlux(User.class);
    }

    @Override
    public Mono<User> getUser(int id) {
        return webClient
                .get()
                .uri("/user/" + id)
                .retrieve()
                .bodyToMono(User.class);
    }

    @Override
    public Mono<Void> save(Mono<User> user) {
        return webClient
                .post()
                .uri("/user/save")
                .body(Mono.just(user).block(), User.class)
                .retrieve()
                .bodyToMono(Void.class);
    }

    @Override
    public Mono<User> update(Mono<User> user) {
        return null;
    }


    @Override
    public Mono<Void> delete(int id) {
        return webClient.delete()
                .uri("/user/" + id)
                .retrieve()
                .bodyToMono(Void.class);
    }

    @Override
    public Mono<Object> getCredit(int id) {
        return webClient
                .get()
                .uri("/enquiry/report/" + id)
                .retrieve()
                .bodyToMono(Object.class);
    }


}
