package com.example.app.repository;

import com.example.app.config.PropertiesFileInit;
import com.example.app.entity.Role;
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

@Testcontainers
public class RoleRepositoryTest {
    @Container
    public static PostgreSQLContainer<?> databaseContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("workers_and_relations")
            .withUsername(PropertiesFileInit.getProperties("username"))
            .withPassword(PropertiesFileInit.getProperties("password"))
            .withExposedPorts(5432)
            .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                    new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(5432), new ExposedPort(5432)))
            )).withInitScript("schema.sql");

    private static RoleRepository roleRepository;

    private static JdbcDatabaseDelegate jdbcDatabaseDelegate;

    @BeforeAll
    static void beforeAll() {
        databaseContainer.start();
        roleRepository = new RoleRepository();
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
    void testSaveRole() {
        String expectedName = "Manager";
        Role role = new Role(null,expectedName);
        role = roleRepository.saveRole(role);
        Role roleById = roleRepository.findRoleById(role.getId());

        Assertions.assertEquals(expectedName, roleById.getName());
    }

    @Test
    void testUpdateRole() {
        String expectedName = "Chef";

        Role roleForUpdate = roleRepository.findRoleById(3L);
        String oldRoleName = roleForUpdate.getName();

        roleForUpdate.setName(expectedName);
        roleRepository.updateRole(roleForUpdate);

        Role role = roleRepository.findRoleById(3L);

        Assertions.assertNotEquals(expectedName, oldRoleName);
        Assertions.assertEquals(expectedName, role.getName());
    }

    @Test
    void testDeleteRoleById() {

        int expectedSize = roleRepository.findAllRoles().size();

        Role tempRole = new Role(null, "Manager");
        tempRole = roleRepository.saveRole(tempRole);

        roleRepository.deleteRoleById(tempRole.getId());
        int roleListAfterSize = roleRepository.findAllRoles().size();

        Assertions.assertEquals(expectedSize, roleListAfterSize);
    }

    @Test
    void testFindAllRoles() {
        int expectedSize = 3;
        int resultSize = roleRepository.findAllRoles().size();

        Assertions.assertEquals(expectedSize, resultSize);
    }
}
