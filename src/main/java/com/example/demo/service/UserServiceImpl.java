package com.example.demo.service;

import com.example.demo.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String MOCK_DATA = "sample.json";

    @Override
    public Flux<User> getAll() {
        List<User> userList = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            userList = objectMapper.readValue(
                    new File(MOCK_DATA),
                    new TypeReference<List<User>>() {
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Flux.fromIterable(userList);
    }

    @Override
    public Mono<User> getUser(int id) {

        User user = null;

        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(MOCK_DATA));

            JSONArray jsonArr = (JSONArray) obj;
            JSONObject jsonObj = (JSONObject) jsonArr.get(id);

            ObjectMapper objectMapper = new ObjectMapper();
            user = objectMapper.readValue(jsonObj.toString(), User.class);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Mono.just(user);
    }

    @Override
    public Mono<User> getUser(String firstName) {
        return null;
    }
}
