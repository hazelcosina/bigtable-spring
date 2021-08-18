package com.example.demo.service;

import com.example.demo.repository.BigTableRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class BigTableServiceImpl implements BigTableService {

    private final BigTableRepository bigTableRepository;

    @Override
    public Flux<Object> getAll() {
        return bigTableRepository.getAll();
    }

    @Override
    public Mono<Void> save(Object object) { return bigTableRepository.save(object); }

    @Override
    public Mono<Void> deleteAll() {
        return bigTableRepository.deleteAll();
    }
}
