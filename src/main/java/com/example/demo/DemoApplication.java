package com.example.demo;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

//		WireMockServer wireMockServer = new WireMockServer(8082); //No-args constructor will start on port 8080, no HTTPS
//		wireMockServer.start();
//		configureFor("localhost", 8082);
//
//
//		stubFor(get(urlEqualTo("/resource")).willReturn(aResponse()
//				.withHeader("Content-Type", "text/plain").withBody("Hello World!")));
//
//		stubFor(get("/some/thing")
//				.willReturn(aResponse().withStatus(200)));
	}

}
