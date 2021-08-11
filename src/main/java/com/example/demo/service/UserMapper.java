package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.UserPayload;
import com.google.cloud.bigtable.data.v2.models.Row;

public interface UserMapper {
    User mapToUser(Row row);
    User mapToUser(UserPayload userPayload);
}
