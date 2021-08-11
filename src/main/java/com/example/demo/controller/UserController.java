package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.UserPayload;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("api/applications")
    public Flux<User> getUser() {
        return userService.getAll();
    }

    @PostMapping("api/applications")
    public Mono<Void> saveUser(@RequestBody UserPayload user) {
        return userService.save(user);

    }

    @DeleteMapping("api/applications")
    public Mono<Void> deleteUser() {
        return userService.deleteAll();
    }
}
