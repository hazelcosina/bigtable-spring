package com.example.demo.service;

import com.example.demo.repository.BigTableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BigTableServiceImplTest {

    @Mock
    private BigTableRepository bigTableRepository;

    private BigTableService bigTableService;

    @BeforeEach
    public void setup() {
        bigTableService = new BigTableServiceImpl(bigTableRepository);
    }

    @Test
    public void shouldGetAll() {
        List<String> strList = List.of("test1", "test2");
        Flux<Object> obj = Flux.fromIterable(strList);

        when(bigTableRepository.getAll()).thenReturn(obj);

        Flux<Object> actual = bigTableService.getAll();

        assertEquals("test1", actual.elementAt(0).block());
        assertEquals("test2", actual.elementAt(1).block());
    }

    @Test
    public void shouldSave() {
        Mono<Void> resp = Mono.empty();
        Object obj = new Object();

        given(bigTableRepository.save(obj)).willReturn(resp);
        StepVerifier
                .create(bigTableRepository.save(obj))
                .verifyComplete();
    }

    @Test
    public void shouldDelete() {
        Mono<Void> resp = Mono.empty();
        given(bigTableRepository.deleteAll()).willReturn(resp);
        StepVerifier
                .create(bigTableRepository.deleteAll())
                .verifyComplete();
    }
}