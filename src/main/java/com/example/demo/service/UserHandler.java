package com.example.demo.service;

import com.example.demo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserService userService;

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ServerResponse.ok()
                .body(userService.getAll(), User.class);
    }

    public Mono<ServerResponse> getUserById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return ServerResponse.ok()
                .body(userService.getUser(id), User.class);
    }
}
