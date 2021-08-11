package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter @NoArgsConstructor
public class UserPayload {

    private Status status;
    private Timestamp timestamp;
    private Application application;

}
