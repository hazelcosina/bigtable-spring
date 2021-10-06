package com.example.demo.service;

import com.example.demo.config.TWebClient;
import com.example.demo.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest
@ContextConfiguration(classes = {UserRouter.class, UserHandler.class, UserServiceImpl.class, TWebClient.class})
@AutoConfigureWireMock
class UserRouterTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void getAll() {

        stubFor(get(("/user"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{" +
                                "\"id\":0,\"firstName\":\"John\"," +
                                "\"lastName\":\"Doe\",\"age\":21}," +
                                "{\"id\":1,\"firstName\":\"Katy\"," +
                                "\"lastName\":\"Perry\",\"age\":20}]"
                        )));

        webTestClient.get().uri("/user")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(User.class);
    }

    @Test
    void getUser() {

        stubFor(get(("/user/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{" +
                                "\"id\":1," +
                                "\"firstName\":\"John\"," +
                                "\"lastName\":\"Doe\"," +
                                "\"age\":21}"
                        )));

        webTestClient.get().uri("/user/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.firstName").isEqualTo("John")
                .jsonPath("$.lastName").isEqualTo("Doe")
                .jsonPath("$.age").isEqualTo(21);
    }

    @Test
    void save() {

        stubFor(post(("/user/save"))
                .withRequestBody( matchingJsonPath("$.id"))
                .withRequestBody( matchingJsonPath("$.firstName"))
                .withRequestBody( matchingJsonPath("$.lastName"))
                .withRequestBody( matchingJsonPath("$.age"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("Created")));

        User user = new User();
        user.setId(1);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setAge(21);

        webTestClient.post()
                .uri("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(user))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON);
    }

//    @Test
//    void update() {
//
//        stubFor(put(urlPathEqualTo("/user/update"))
//                .withRequestBody( matchingJsonPath("$[?(@.id =~ /^[0-9]*/)]"))
//                .withRequestBody( matchingJsonPath("$[?(@.firstName =~ /[a-zA-Z]*/)]"))
//                .withRequestBody( matchingJsonPath("$[?(@.lastName =~ /[a-zA-Z]*/)]"))
//                .withRequestBody( matchingJsonPath("$[?(@.age =~ /[0-9]*/)]"))
//                .willReturn(aResponse()
//                        .withStatus(200)
//                        .withHeader("Content-Type", "application/json"
//                        )));
//
//        User user = new User();
//        user.setId(1);
//        user.setFirstName("John");
//        user.setLastName("Doe");
//        user.setAge(21);
//
//        webTestClient.put()
//                .uri("/user")
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromValue(user))
//                .exchange()
//                .expectStatus().isOk()
//                .expectHeader().contentType(MediaType.APPLICATION_JSON);
//    }

    @Test
    void deleteById() {

        stubFor(delete("/user/1")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{" +
                                "\"id\":1," +
                                "\"firstName\":\"John\"," +
                                "\"lastName\":\"Doe\"," +
                                "\"age\":21}"
                        )));

        webTestClient.delete().uri("/user/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }
}