package com.example.demo.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class BigTableRouter {

    @Bean
    public RouterFunction<ServerResponse> route(BigTableHandler bigTableHandler) {

        return RouterFunctions.route()
                .GET("/api/applications", bigTableHandler::getAll)
                .POST("/api/applications", bigTableHandler::create)
                .DELETE("/api/applications", bigTableHandler::deleteAll)
                .build();
    }

}
