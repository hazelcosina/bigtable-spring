package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter @NoArgsConstructor
public class User {
    private String name;
    private String surName;
    private Integer age;
    private Status status;
    private Timestamp timestamp;
}
