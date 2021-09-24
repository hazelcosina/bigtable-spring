package com.example.demo.service;

import com.example.demo.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

//    @Value("${mock.data.file}")
//    private String jsonFile;

    @Override
    public Flux<User> getAll() {

        List<User> userList = new ArrayList<>();
        User katy = new User();
        katy.setId(1);
        katy.setFirstName("Katy");
        katy.setLastName("Perry");
        katy.setAge(20);
        userList.add(katy);

        User brad = new User();
        brad.setId(2);
        brad.setFirstName("Brad");
        brad.setLastName("Pitt");
        brad.setAge(20);
        userList.add(brad);

        return Flux.fromIterable(userList);
    }

    @Override
    public Mono<User> getUser(long id) {
        User katy = new User();
        katy.setId(1);
        katy.setFirstName("Katy");
        katy.setLastName("Perry");
        katy.setAge(20);


        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = null;
        List<User> userList = null;
        try {
            userList = objectMapper.readValue("sample.json",new TypeReference<List<User>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(userList);

        return Mono.just(katy);
    }

    @Override
    public Mono<User> getUser(String firstName) {
        return null;
    }
}
