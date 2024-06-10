package com.example.app.repository;

import com.example.app.config.PropertiesFileInit;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

public class WorkerRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> databaseContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("workers_and_relations")
            .withUsername(PropertiesFileInit.getProperties("username"))
            .withPassword(PropertiesFileInit.getProperties("password"))
            .withExposedPorts(5432)
            .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                    new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(5432), new ExposedPort(5432)))
            )).withInitScript("schema.sql");


}
