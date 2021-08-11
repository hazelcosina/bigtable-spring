package com.example.demo.repository;

import com.example.demo.model.UserConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.api.gax.rpc.ServerStream;
import com.google.cloud.bigtable.data.v2.BigtableDataClient;
import com.google.cloud.bigtable.data.v2.models.Query;
import com.google.cloud.bigtable.data.v2.models.Row;
import com.google.cloud.bigtable.data.v2.models.RowCell;
import com.google.cloud.bigtable.data.v2.models.RowMutation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final BigtableDataClient dataClient;
    private final ObjectMapper mapper;

    @Override
    public Flux<Object> getAll() {
        Query query = Query.create(UserConstants.TABLE_ID);
        ServerStream<Row> rowStream = dataClient.readRows(query);

        List<JsonNode> nodes = new ArrayList<>();
        rowStream.forEach(row -> {
            JsonNode objNode = mapper.createObjectNode();
            for (RowCell cell : row.getCells()) {
                JsonNode newNode = mapper.valueToTree(cell.getValue().toStringUtf8());
                ((ObjectNode) objNode).set(cell.getQualifier().toStringUtf8(), newNode);
            }
            nodes.add(objNode);
        });
        return Flux.fromIterable(nodes);
    }

    @Override
    public Mono<Void> save(Object object) {

        RowMutation.create(UserConstants.TABLE_ID, UserConstants.PREFIX );
        RowMutation rowMutation = RowMutation.create(UserConstants.TABLE_ID, UserConstants.PREFIX + UUID.randomUUID().toString());

        JsonNode root = mapper.valueToTree(object);
        Iterator<Map.Entry<String, JsonNode>> rootFields = root.fields();

        while (rootFields.hasNext()) {
            Map.Entry<String, JsonNode> rootEntry = rootFields.next();
            if (rootEntry.getValue().isContainerNode()) {
                Iterator<Map.Entry<String, JsonNode>> fields = rootEntry.getValue().fields();
                while (fields.hasNext()) {
                    Map.Entry<String, JsonNode> entry = fields.next();
                    rowMutation.setCell(UserConstants.COLUMN_FAMILY, entry.getKey(), entry.getValue().asText());
                }
            } else {
                rowMutation.setCell(UserConstants.COLUMN_FAMILY, rootEntry.getKey(), rootEntry.getValue().asText());
            }
            dataClient.mutateRow(rowMutation);
        }

        return Mono.empty();
    }

    @Override
    public Mono<Void> deleteAll() {

        Query query = Query.create(UserConstants.TABLE_ID).prefix(UserConstants.PREFIX);
        ServerStream<Row> rows = dataClient.readRows(query);
        for (Row row : rows) {
           dataClient.mutateRowAsync(RowMutation.create(UserConstants.TABLE_ID, row.getKey()).deleteRow());
        }
        return Mono.empty();
    }
}
