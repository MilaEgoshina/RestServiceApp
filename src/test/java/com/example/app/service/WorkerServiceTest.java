package com.example.app.service;

import com.example.app.dto.IncomingWorkRelationsDTO;
import com.example.app.dto.OutgoingWorkRelationsDTO;
import com.example.app.dto.UpdateWorkRelationsDTO;
import com.example.app.entity.Role;
import com.example.app.entity.WorkRelations;
import com.example.app.exception.NotFoundException;
import com.example.app.repository.WorkRelationsRepository;
import com.example.app.repository.WorkerAndRelationsRepository;
import com.example.app.repository.WorkerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Optional;

public class WorkerServiceTest {

    private static WorkerService workerService;
    private static Role role;
    private static WorkerRepository workerMockRepository;

    @BeforeAll
    static void start(){
        workerMockRepository = Mockito.mock(WorkerRepository.class);
        role = new Role(1L, "HR");
        workerService = new WorkerService();
    }

    @BeforeEach
    void setUp() {
        Mockito.reset(workerMockRepository);
    }

}
