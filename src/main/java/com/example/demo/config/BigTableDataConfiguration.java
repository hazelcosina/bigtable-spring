package com.example.demo.config;

import com.google.cloud.bigtable.admin.v2.BigtableTableAdminClient;
import com.google.cloud.bigtable.admin.v2.models.CreateTableRequest;
import com.google.cloud.bigtable.data.v2.BigtableDataClient;
import com.google.cloud.bigtable.data.v2.BigtableDataSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.cloud.bigtable.admin.v2.BigtableTableAdminSettings;

import java.io.IOException;

@Configuration
public class BigTableDataConfiguration {

    @Value("${bigtable.emulator.port}")
    private int port;

    @Value("${bigtable.projectid}")
    private String projectId;

    @Value("${bigtable.instanceid}")
    private String instanceId;

    @Value("${bigtable.tableid}")
    private String tableId;

    @Bean
    public BigtableDataClient dataClient() throws IOException {
        BigtableDataSettings settings = BigtableDataSettings.newBuilder().setProjectId(projectId).setInstanceId(instanceId).build();
        return BigtableDataClient.create(settings);
    }

    @Bean
    @ConditionalOnProperty(prefix = "bigtable.emulator", name = "enabled")
    public BigtableTableAdminClient adminClient() throws IOException {

        BigtableTableAdminSettings adminSettings = BigtableTableAdminSettings.newBuilderForEmulator(port)
                .setProjectId(projectId)
                .setInstanceId(instanceId)
                .build();

        BigtableTableAdminClient adminClient = BigtableTableAdminClient.create(adminSettings);
        if (!adminClient.exists(tableId)) {
            CreateTableRequest createTableRequest = CreateTableRequest.of(tableId)
                    .addFamily("users");
            adminClient.createTable(createTableRequest);

            System.out.printf("Table %s created successfully%n", tableId);
        }
        return adminClient;
    }
}
