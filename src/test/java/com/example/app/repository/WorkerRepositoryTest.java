package com.example.app.repository;

import com.example.app.config.PropertiesFileInit;
import com.example.app.entity.Computer;
import com.example.app.entity.Role;
import com.example.app.entity.WorkRelations;
import com.example.app.entity.Worker;
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

    private static WorkerRepository workerRepository;
    private static JdbcDatabaseDelegate jdbcDatabaseDelegate;

    @BeforeAll
    static void beforeAll() {
        databaseContainer.start();
        workerRepository = new WorkerRepository();
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
    void testSaveWorker() {
        String expectedFirstName = "John";
        String expectedLastName = "Doe";

        Worker worker = new Worker(null, expectedFirstName, expectedLastName, null, null, null);
        worker = workerRepository.saveWorker(worker);
        Worker result = workerRepository.findWorkerById(worker.getId());

        Assertions.assertEquals(expectedFirstName, result.getFirstName());
        Assertions.assertEquals(expectedLastName, result.getLastName());
    }

    @Test
    void update() {
        String expectedFirstname = "Margo";
        String expectedLastname = "Jackson";
        Long expectedRoleId = 1L;

        Worker worker = workerRepository.findWorkerById(3L);

        List<WorkRelations> workRelationsList = worker.getWorkRelationsList();
        int computerListSize = worker.getComputerList().size();
        int workRelationsListSize = worker.getWorkRelationsList().size();
        Role oldRole = worker.getRole();

        Assertions.assertNotEquals(expectedRoleId, worker.getRole().getId());
        Assertions.assertNotEquals(expectedFirstname, worker.getFirstName());
        Assertions.assertNotEquals(expectedLastname, worker.getLastName());

        worker.setFirstName(expectedFirstname);
        worker.setLastName(expectedLastname);
        workerRepository.updateWorker(worker);

        Worker result = workerRepository.findWorkerById(3L);

        Assertions.assertEquals(expectedFirstname, result.getFirstName());
        Assertions.assertEquals(expectedLastname, result.getLastName());

        Assertions.assertEquals(computerListSize, result.getComputerList().size());
        Assertions.assertEquals(workRelationsListSize, result.getWorkRelationsList().size());
        Assertions.assertEquals(oldRole.getId(), result.getRole().getId());

        worker.setComputerList(List.of());
        worker.setWorkRelationsList(List.of());
        worker.setRole(new Role(expectedRoleId, null));
        workerRepository.updateWorker(worker);
        result = workerRepository.findWorkerById(3L);

        Assertions.assertEquals(0, result.getComputerList().size());
        Assertions.assertEquals(0, result.getWorkRelationsList().size());
        Assertions.assertEquals(expectedRoleId, result.getRole().getId());

        workRelationsList.add(new WorkRelations(3L, null, null));
        workRelationsList.add(new WorkRelations(4L, null, null));
        worker.setWorkRelationsList(workRelationsList);
        workerRepository.updateWorker(worker);
        result = workerRepository.findWorkerById(3L);

        Assertions.assertEquals(3, result.getWorkRelationsList().size());

        workRelationsList.remove(2);
        worker.setWorkRelationsList(workRelationsList);
        workerRepository.updateWorker(worker);
        result = workerRepository.findWorkerById(3L);

        Assertions.assertEquals(2, result.getWorkRelationsList().size());

        worker.setComputerList(List.of(
                new Computer(null, "1111111", null),
                new Computer(null, "222222", null)));
        worker.setWorkRelationsList(List.of(new WorkRelations(1L, null, null)));

        workerRepository.updateWorker(worker);
        result = workerRepository.findWorkerById(3L);

        Assertions.assertEquals(1, result.getComputerList().size());
        Assertions.assertEquals(1, result.getWorkRelationsList().size());
    }

    @Test
    void testDeleteByWorkerId() {

        int expectedSize = workerRepository.findAllWorkers().size();

        Worker worker = new Worker(null, "John", "Doe", null, null, null);
        worker = workerRepository.saveWorker(worker);

        workerRepository.deleteWorkerById(worker.getId());
        int workerListAfterDelete = workerRepository.findAllWorkers().size();

        Assertions.assertEquals(expectedSize, workerListAfterDelete);
    }

    @Test
    void testFindAllWorkers() {
        int expectedSize = 5;
        int resultSize = workerRepository.findAllWorkers().size();

        Assertions.assertEquals(expectedSize, resultSize);
    }

    @Test
    void testFindById_existingId() {
        Long expectedId = 1L;
        Worker worker = workerRepository.findWorkerById(expectedId);
        Assertions.assertEquals(expectedId, worker.getId());
    }


}

