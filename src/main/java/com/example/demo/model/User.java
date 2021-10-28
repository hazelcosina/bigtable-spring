package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Data
@Slf4j
public class User {
    private int id;
    @NotBlank(message = "Name is mandatory")
    private String firstName;
    private String lastName;
    private int age;
}
