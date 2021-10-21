package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

@Configuration
public class TWebClient {

    @Bean
    WebClient webClient() {

        HttpClient httpClient = HttpClient.create()
                .tcpConfiguration(tcpClient ->
                        tcpClient.proxy(proxy -> proxy.type(ProxyProvider.Proxy.HTTP).host("myproxyhost")));
        ReactorClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

        return WebClient.builder()
                .clientConnector(connector)
                .baseUrl("http://localhost:8080")
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                })
                .build();
    }
}
