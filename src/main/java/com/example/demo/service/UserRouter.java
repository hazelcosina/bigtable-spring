package com.example.demo.service;

import com.example.demo.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.net.URI;

import static java.lang.Integer.parseInt;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class UserRouter {

    @Bean
    @RouterOperation(path = "/user/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.DELETE, beanClass = UserService.class, beanMethod = "delete"
            , operation = @Operation(operationId = "delete", responses = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid Employee ID supplied"),
            @ApiResponse(responseCode = "404", description = "Employee not found")}, parameters = {
            @Parameter(in = ParameterIn.PATH, name = "id")}
    ))
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
