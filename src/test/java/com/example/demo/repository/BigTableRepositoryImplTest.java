package com.example.demo.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFutures;
import com.google.api.gax.rpc.ServerStream;
import com.google.bigtable.v2.ReadRowsResponse;
import com.google.cloud.bigtable.data.v2.BigtableDataClient;
import com.google.cloud.bigtable.data.v2.models.Row;
import com.google.cloud.bigtable.data.v2.models.RowCell;
import com.google.cloud.bigtable.data.v2.stub.EnhancedBigtableStub;
import com.google.common.collect.ImmutableList;
import com.google.protobuf.ByteString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BigtableDataClient.class})
class BigTableRepositoryImplTest {

    @MockBean
    private BigtableDataClient dataClient;

    @MockBean
    private ObjectMapper mapper;

    private BigTableRepositoryImpl repo;
    private EnhancedBigtableStub stub;


    @BeforeEach
    public void setup() {
        repo = new BigTableRepositoryImpl(dataClient, mapper);
    }

    @Test
    public void getAll() {

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
        List<Row> rows = new ArrayList<>();
        rows.add(row);

//        ServerStream<Row> stream = mock(ServerStream.class);
//        when(stream.iterator()).thenReturn(rows.iterator());

        Row expectedRow =
                Row.create(ByteString.copyFromUtf8("fake-row-key"), ImmutableList.<RowCell>of());

        when(dataClient.readRows(Mockito.any())).thenReturn((ServerStream) ApiFutures.immediateFuture(expectedRow));

        Flux<Object> expected = repo.getAll();
        Object first = expected.single().block();

        System.out.println(first);
        assertNotNull(expected);


    }
}