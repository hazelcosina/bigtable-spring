package com.example.demo.service;

import com.example.demo.model.*;
import com.google.cloud.bigtable.data.v2.models.Row;
import com.google.cloud.bigtable.data.v2.models.RowCell;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class UserMapperImpl implements UserMapper {

    @Override
    public User mapToUser(Row row) {
        if (row == null) {
            return null;
        }
        User user = new User();

        RowCell name = row.getCells(UserConstants.COLUMN_FAMILY, UserConstants.COLUMN_QUALIFIER_NAME).get(0);
        RowCell surName = row.getCells(UserConstants.COLUMN_FAMILY, UserConstants.COLUMN_QUALIFIER_SURNAME).get(0);
        RowCell age = row.getCells(UserConstants.COLUMN_FAMILY, UserConstants.COLUMN_QUALIFIER_AGE).get(0);
        RowCell status = row.getCells(UserConstants.COLUMN_FAMILY, UserConstants.COLUMN_QUALIFIER_STATUS).get(0);
        RowCell timestamp = row.getCells(UserConstants.COLUMN_FAMILY, UserConstants.COLUMN_QUALIFIER_TIMESTAMP).get(0);

        user.setName(name.getValue().toStringUtf8());
        user.setSurName(surName.getValue().toStringUtf8());
        user.setAge(Integer.parseInt(age.getValue().toStringUtf8()));
        user.setStatus(Status.valueOf(status.getValue().toStringUtf8()));
        user.setTimestamp(Timestamp.valueOf(timestamp.getValue().toStringUtf8()));

        return user;
    }

    @Override
    public User mapToUser(UserPayload userPayload) {

        if (userPayload == null) {
            return null;
        }

        User user = new User();

        user.setStatus(userPayload.getStatus());
        user.setTimestamp(userPayload.getTimestamp());

        Application application = userPayload.getApplication();
        user.setName(application.getName());
        user.setSurName(application.getSurname());
        user.setAge(application.getAge());

        return user;
    }
}
