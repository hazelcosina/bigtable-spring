package com.example.demo.service;

import com.example.demo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

import static java.lang.Integer.parseInt;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class UserRouter {

    @Bean
    public RouterFunction<ServerResponse> userRoutes(UserService userService) {
        return route().nest(RequestPredicates.path("/user"),
                builder -> {
                    builder.GET("/credit-enquiry/{id}", req -> ok().body(userService.getCredit(parseInt(req.pathVariable("id"))), Object.class));
                    builder.GET("/{id}", req -> ok().body(userService.getUser(parseInt(req.pathVariable("id"))), User.class));
                    builder.DELETE("/{id}", req -> ok().body(userService.delete(parseInt(req.pathVariable("id"))), User.class));
                    builder.GET(req -> ok().body(userService.getAll(), User.class));
                    builder.POST(req -> created(URI.create("/")).body(userService.save(req.bodyToMono(User.class)), User.class));
                    builder.PUT(req -> ok().body(userService.update(req.bodyToMono(User.class)), User.class));
                }).build();
    }
}
