package com.example.demo.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class UserRouter {

    @Bean
    public RouterFunction<ServerResponse> route(UserHandler userHandler) {

        return RouterFunctions.route()
                .route(GET("/user"), userHandler::getAll)
                .route(GET("/user/{id}").and(accept(MediaType.APPLICATION_JSON)), userHandler::getUserById)
                .route(POST("/user").and(accept(MediaType.APPLICATION_JSON)), userHandler::createUser)
                .route(PUT("/user").and(accept(MediaType.APPLICATION_JSON)), userHandler::delete)
                .route(DELETE("/user/{id}").and(accept(MediaType.APPLICATION_JSON)), userHandler::delete)
                .build();
    }

}
