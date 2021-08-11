package com.example.demo.repository;

import com.example.demo.model.User;
import com.example.demo.model.UserConstants;
import com.example.demo.service.UserMapper;
import com.google.api.client.util.Lists;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.api.gax.rpc.ServerStream;
import com.google.cloud.bigtable.data.v2.BigtableDataClient;
import com.google.cloud.bigtable.data.v2.models.Query;
import com.google.cloud.bigtable.data.v2.models.Row;
import com.google.cloud.bigtable.data.v2.models.RowMutation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final BigtableDataClient dataClient;
    private final UserMapper userMapper;

    @Override
    public Flux<User> getAll() {
        Query query = Query.create(UserConstants.TABLE_ID);
        ServerStream<Row> rowStream = dataClient.readRows(query);

        List<User> users = new ArrayList<>();
        rowStream.forEach(row -> {
            User user = userMapper.mapToUser(row);
            users.add(user);
        });
        return Flux.fromIterable(users);
    }

    @Override
    public Mono<Void> save(User user) {
        RowMutation rowMutation =
                RowMutation.create(UserConstants.TABLE_ID, UserConstants.PREFIX + UUID.randomUUID().toString())
                        .setCell(UserConstants.COLUMN_FAMILY, UserConstants.COLUMN_QUALIFIER_NAME, user.getName())
                        .setCell(UserConstants.COLUMN_FAMILY, UserConstants.COLUMN_QUALIFIER_SURNAME, user.getSurName())
                        .setCell(UserConstants.COLUMN_FAMILY, UserConstants.COLUMN_QUALIFIER_AGE, user.getAge().toString())
                        .setCell(UserConstants.COLUMN_FAMILY, UserConstants.COLUMN_QUALIFIER_STATUS, user.getStatus().getValue())
                        .setCell(UserConstants.COLUMN_FAMILY, UserConstants.COLUMN_QUALIFIER_TIMESTAMP, user.getTimestamp().toString());

        dataClient.mutateRow(rowMutation);

        return Mono.empty();
    }

    @Override
    public Mono<Void> deleteAll() {
        Query query = Query.create(UserConstants.TABLE_ID).prefix(UserConstants.PREFIX);
        List<ApiFuture<Void>> futures = Lists.newArrayList();
        ServerStream<Row> rows = dataClient.readRows(query);
        for (Row row : rows) {
            ApiFuture<Void> future =
                    dataClient.mutateRowAsync(RowMutation.create(UserConstants.TABLE_ID, row.getKey()).deleteRow());
            futures.add(future);
        }
        ApiFutures.allAsList(futures);
        return Mono.empty();
    }
}
