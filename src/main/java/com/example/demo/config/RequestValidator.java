package com.example.demo.config;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Set;

@Component
public class RequestValidator {

    final Validator validator;

    public RequestValidator(Validator validator) {
        this.validator = validator;
    }

    public <T> Mono<T> validate(T obj) {

        if (obj == null) {
            return Mono.error(new IllegalArgumentException());
        }
        String jsonStr = validateJson(obj);

        validSchema(jsonStr);

        Set<ConstraintViolation<T>> violations = this.validator.validate(obj);
        if (violations == null || violations.isEmpty()) {
            return Mono.just(obj);
        }

        return Mono.error(new ConstraintViolationException(violations));
    }

    private boolean validSchema(String jsonStr){

        JSONObject jsonSchema = new JSONObject(
                new JSONTokener(RequestValidator.class.getResourceAsStream("/static/schema.json")));
        JSONObject jsonSubject = new JSONObject(
                new JSONTokener(jsonStr));

        Schema schema = SchemaLoader.load(jsonSchema);
        schema.validate(jsonSubject);
        return true;
    }

    private String validateJson(Object obj) {
        try{
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e){
            e.printStackTrace();
            Mono.error(new ValidationException());
        }
        return "";
    }

}
