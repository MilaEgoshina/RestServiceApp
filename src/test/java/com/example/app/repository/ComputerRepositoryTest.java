package com.example.app.repository;

import com.example.app.config.PropertiesFileInit;
import com.example.app.entity.Computer;
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

import static org.junit.Assert.assertEquals;

@Testcontainers
public class ComputerRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> databaseContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("workers_and_relations")
            .withUsername(PropertiesFileInit.getProperties("username"))
            .withPassword(PropertiesFileInit.getProperties("password"))
            .withExposedPorts(5432)
            .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                    new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(5432), new ExposedPort(5432)))
            )).withInitScript("schema.sql");

    private static ComputerRepository computerRepository;

    private static JdbcDatabaseDelegate jdbcDatabaseDelegate;

    @BeforeAll
    static void beforeAll() {
        databaseContainer.start();
        computerRepository = new ComputerRepository();
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
    void testSaveComputer() {
        String expectedSerialNumber = "111111111";
        Computer computer = new Computer(null, expectedSerialNumber, null);
        computer = computerRepository.saveComputer(computer);
        Computer resultPhone = computerRepository.findComputerById(computer.getId());

        Assertions.assertEquals(expectedSerialNumber, resultPhone.getSerialNumber());
    }

    @Test
    void testUpdateComputer() {
        String expectedSerialNumber = "111111111";

        Computer computer = computerRepository.findComputerById(3L);
        String oldComputer = computer.getSerialNumber();

        computer.setSerialNumber(expectedSerialNumber);
        computerRepository.updateComputer(computer);

        Computer computer1 = computerRepository.findComputerById(3L);

        Assertions.assertNotEquals(expectedSerialNumber, oldComputer);
        Assertions.assertEquals(expectedSerialNumber, computer1.getSerialNumber());
    }

    @Test
    void testDeleteComputerById() {
        Boolean expectedValue = true;
        int expectedSize = computerRepository.findAllComputers().size();

        Computer computer = new Computer(null, "2222222", null);
        computer = computerRepository.saveComputer(computer);

        int sizeBefore = computerRepository.findAllComputers().size();
        Assertions.assertNotEquals(expectedSize, sizeBefore);

        computerRepository.deleteComputerById(computer.getId());
        int resultSizeListAfter = computerRepository.findAllComputers().size();

        Assertions.assertEquals(expectedSize, resultSizeListAfter);
    }

    @Test
    void testDeleteByWorkerId() {

        int expectedSize = computerRepository.findAllComputers().size() - computerRepository.findAllComputersByWorkerId(1L).size();

        computerRepository.deleteComputerByWorkerId(1L);

        int resultSize = computerRepository.findAllComputers().size();
        Assertions.assertEquals(expectedSize, resultSize);
    }

    @Test
    void testFindAllComputers() {

        int expectedSize = 5;
        int result = computerRepository.findAllComputers().size();

        Assertions.assertEquals(expectedSize, result);
    }

    @Test
    void testFindComputerByItsNumber() {

        String existingNumber = "JKL56789";
        String nonExistingNumber = "not exits number";

        Computer computer = computerRepository.findComputerBySerialNumber(existingNumber);
        assertEquals(existingNumber, computer.getSerialNumber());
    }

    @Test
    void testFindComputerById() {

        Long existingId = 1L;

        Computer computer = computerRepository.findComputerById(existingId);
        assertEquals(existingId, computer.getId());

    }
}

