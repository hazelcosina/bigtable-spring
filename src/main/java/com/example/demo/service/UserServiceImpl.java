package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.UserPayload;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Flux<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public Mono<Void> save(UserPayload object) {
        User user = userMapper.mapToUser(object);
        return userRepository.save(user);
    }

    @Override
    public Mono<Void> deleteAll() {
        return userRepository.deleteAll();
    }
}
