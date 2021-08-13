package com.example.demo.repository;

import com.example.demo.model.BigTableConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.gax.rpc.ServerStream;
import com.google.cloud.bigtable.data.v2.BigtableDataClient;
import com.google.cloud.bigtable.data.v2.models.Query;
import com.google.cloud.bigtable.data.v2.models.Row;
import com.google.cloud.bigtable.data.v2.models.RowCell;
import com.google.cloud.bigtable.data.v2.stub.EnhancedBigtableStub;
import com.google.protobuf.ByteString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


class BigTableRepositoryImplTest {

    @MockBean
    private BigtableDataClient dataClient;
    @MockBean
    private ObjectMapper mapper;

    private BigTableRepositoryImpl repo;
    private EnhancedBigtableStub stub;


    @BeforeEach
    public void setup(){
        repo = new BigTableRepositoryImpl(dataClient, mapper);
    }

    @Test
    public void getAll() {
        List<Row> rowList = new ArrayList<>();

        Row row = new Row() {
            @Override
            public ByteString getKey() {
                return ByteString.copyFromUtf8("name");
            }
            @Override
            public List<RowCell> getCells() {
                List<RowCell> rowCells = new ArrayList<>();
                rowCells.add(new RowCell() {
                    @Override
                    public String getFamily() {
                        return "users";
                    }
                    @Override
                    public ByteString getQualifier() {
                        return null;
                    }
                    @Override
                    public long getTimestamp() {
                        return 0;
                    }
                    @Override
                    public ByteString getValue() {
                        return ByteString.copyFromUtf8("Brad");
                    }
                    @Override
                    public List<String> getLabels() {
                        return null;
                    }

                });
                return rowCells;
            }
        };
        rowList.add(row);

        ServerStream<Row> stream = (ServerStream) rowList;
        Query query = Query.create(BigTableConstants.TABLE_ID);
        when(dataClient.readRows(query)).thenReturn(stream);

        Flux<Object> expected = repo.getAll();
        System.out.println(expected);
    }
}