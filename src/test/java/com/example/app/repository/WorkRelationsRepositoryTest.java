package com.example.app.repository;

import com.example.app.config.PropertiesFileInit;
import com.example.app.entity.WorkRelations;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@Testcontainers
public class WorkRelationsRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> databaseContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("workers_and_relations")
            .withUsername(PropertiesFileInit.getProperties("username"))
            .withPassword(PropertiesFileInit.getProperties("password"))
            .withExposedPorts(5432)
            .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                    new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(5432), new ExposedPort(5432)))
            )).withInitScript("schema.sql");

    private static WorkRelationsRepository workRelationsRepository;
    private static JdbcDatabaseDelegate jdbcDatabaseDelegate;

    @BeforeAll
    static void beforeAll() {
        databaseContainer.start();
        workRelationsRepository = new WorkRelationsRepository();
        jdbcDatabaseDelegate = new JdbcDatabaseDelegate(databaseContainer, "");
    }
    @AfterAll
    static void afterAll() {
        databaseContainer.stop();
    }

    @BeforeEach
    void setUp() {

        ScriptUtils.runInitScript(jdbcDatabaseDelegate, "schema.sql");
    }

    @Test
    void testSaveWorkRelations() {
        String expectedWorkRelations = "Friends";
        WorkRelations workRelations = new WorkRelations(null, expectedWorkRelations, null);
        workRelations = workRelationsRepository.saveWorkRelations(workRelations);
        WorkRelations resultWorkRelations = workRelationsRepository.findRelationsById(workRelations.getId());

        Assertions.assertEquals(expectedWorkRelations, resultWorkRelations.getName());

    }

    @Test
    void testUpdateWorkRelations() {
        String expectedName = "New Work Relations";

        WorkRelations workRelations = workRelationsRepository.findRelationsById(2L);
        String oldName = workRelations.getName();
        int expectedWorkerList = workRelations.getWorkerList().size();
        workRelations.setName(expectedName);
        workRelationsRepository.updateWorkRelations(workRelations);

        WorkRelations resultWorkRelations = workRelationsRepository.findRelationsById(2L);
        int resultSizeUserList = resultWorkRelations.getWorkerList().size();

        Assertions.assertNotEquals(expectedName, oldName);
        Assertions.assertEquals(expectedName, resultWorkRelations.getName());
        Assertions.assertEquals(expectedWorkerList, resultSizeUserList);
    }

    @Test
    void testDeleteWorkerRelationsById() {

        int expectedSize = workRelationsRepository.findAllRelations().size();

        WorkRelations tempWorkRelations = new WorkRelations(null, "New WorkRelations", List.of());
        tempWorkRelations = workRelationsRepository.saveWorkRelations(tempWorkRelations);

        int resultSizeBefore = workRelationsRepository.findAllRelations().size();
        Assertions.assertNotEquals(expectedSize, resultSizeBefore);

        workRelationsRepository.deleteRelationsById(tempWorkRelations.getId());
        int resultSizeAfter = workRelationsRepository.findAllRelations().size();

        Assertions.assertEquals(expectedSize, resultSizeAfter);

    }

    @Test
    void testFindAllRelations() {
        int expectedSize = 4;
        int resultSize = workRelationsRepository.findAllRelations().size();

        Assertions.assertEquals(expectedSize, resultSize);
    }


    @Test
    void findById_existingId() {
        Long expectedId = 1L;
        WorkRelations workRelations = workRelationsRepository.findRelationsById(expectedId);

        Assertions.assertEquals(expectedId, workRelations.getId());
    }
}
