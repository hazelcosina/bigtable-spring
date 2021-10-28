package com.example.demo.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@Configuration
public class WiremockConfiguration {

    @Value("${wiremock.host}")
    private String host;
    @Value("${wiremock.port}")
    private int port;
    @Value("${wiremock.stub.path}")
    private String path;

    @Bean
    void configure() {
        WireMockServer wireMockServer = new WireMockServer(options()
                .port(port)
                .fileSource(new ClasspathFileSourceWithoutLeadingSlash())
                .usingFilesUnderDirectory(path)
                .extensions(new ResponseTemplateTransformer(true))

        );
        wireMockServer.start();
        configureFor(host, port);

    }
}
