//package com.example.demo.controller;
//
//import com.example.demo.service.UserService;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import lombok.AllArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//@RestController
//@AllArgsConstructor
//public class UserController {
//
//    private final UserService userService;
//
//    @GetMapping("api/applications")
//    public Flux<Object> getUser() {
//
//        System.out.println("sds");
//
//        return userService.getAll();
//    }
//
//    @PostMapping("api/applications")
//    public Mono<Void> saveUser(@RequestBody Object user) throws JsonProcessingException {
//        return userService.save(user);
//    }
//
//    @DeleteMapping("api/applications")
//    public Mono<Void> deleteUser() {
//        return userService.deleteAll();
//    }
//}
