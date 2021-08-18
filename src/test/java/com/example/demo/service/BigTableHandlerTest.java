package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest
@ContextConfiguration(classes = {BigTableRouter.class, BigTableHandler.class, BigTableClientService.class, BigTableService.class})
class BigTableHandlerTest {

    @MockBean
    private BigTableService bigTableService;

    @Autowired
    private WebTestClient testClient;

    @Test
    public void shouldGetAll() {
        List<String> strList = List.of("test1", "test2");
        Flux<Object> objectList = Flux.fromIterable(strList);

        when(bigTableService.getAll()).thenReturn(objectList);
        testClient.get()
                .uri("api/applications")
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Object.class);
    }

    @Test
    public void shouldCreate() {

        when(bigTableService.save(new Object())).thenReturn(Mono.empty());
        testClient.post()
                .uri("api/applications")
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Void.class);
    }



    @Test
    public void shouldDeleteAll() {

        when(bigTableService.deleteAll()).thenReturn(Mono.empty());
        testClient.delete()
                .uri("api/applications")
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Object.class);
    }
}