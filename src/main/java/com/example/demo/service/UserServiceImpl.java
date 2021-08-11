package com.example.demo.service;

import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Flux<Object> getAll() {
        return userRepository.getAll();
    }

    @Override
    public Mono<Void> save(Object object) { return userRepository.save(object); }

    @Override
    public Mono<Void> deleteAll() {
        return userRepository.deleteAll();
    }
}
