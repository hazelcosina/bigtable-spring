package com.example.demo.service;

import com.example.demo.model.User;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
@AutoConfigureWireMock
class UserRouterTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void getAll() {

        stubFor(get(urlPathEqualTo("/user"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{\"id\":0,\"firstName\":\"John\",\"lastName\":\"Doe\",\"age\":21},{\"id\":1,\"firstName\":\"Katy\",\"lastName\":\"Perry\",\"age\":20}]"
                        )));

        // Execute and assert
        webTestClient.get().uri("/user")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(User.class);
    }

    @Test
    void getUser() {
        stubFor(get(urlPathEqualTo("/user/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\":0,\"firstName\":\"John\",\"lastName\":\"Doe\",\"age\":21}"
                        )));

        int userId = 1;

        // Execute and assert
        webTestClient.get().uri("/user/{id}",userId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.firstName").isEqualTo("John")
                .jsonPath("$.lastName").isEqualTo("Doe");
    }
//
//    @Test
//    void save() {
//    }
//
//    @Test
//    void update() {
//    }
//
//    @Test
//    void delete() {
//    }
}