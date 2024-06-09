package com.example.app.service;

import com.example.app.dto.IncomingWorkRelationsDTO;
import com.example.app.dto.OutgoingWorkRelationsDTO;
import com.example.app.dto.UpdateWorkRelationsDTO;
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


public class WorkRelationsServiceTest {

    private static WorkRelationsService workRelationsService;
    private static WorkRelationsRepository workRelationsMockRepository;
    private static WorkerRepository workerMockRepository;
    private static WorkerAndRelationsRepository workerAndRelationsMockRepository;

    @BeforeAll
    static void start(){
        workerMockRepository = Mockito.mock(WorkerRepository.class);
        workRelationsMockRepository = Mockito.mock(WorkRelationsRepository.class);
        workerAndRelationsMockRepository = Mockito.mock(WorkerAndRelationsRepository.class);

        workRelationsService = new WorkRelationsService();
    }

    @BeforeEach
    void setUp() {
        Mockito.reset(workRelationsMockRepository);
    }

    @Test
    void saveWorkRelations() {
        Long expectedId = 1L;

        IncomingWorkRelationsDTO incomingWorkRelationsDTO = new IncomingWorkRelationsDTO("Manager");
        WorkRelations workRelations = new WorkRelations(expectedId, "Colleague", null);

        Mockito.doReturn(workRelations).when(workRelationsMockRepository).saveWorkRelations(Mockito.any(WorkRelations.class));

        OutgoingWorkRelationsDTO outgoingWorkRelationsDTO = workRelationsService.saveWorkRelations(incomingWorkRelationsDTO);

        Assertions.assertEquals(expectedId, outgoingWorkRelationsDTO.getId());
    }

    @Test
    void updateWorkRelations() throws NotFoundException {
        Long expectedId = 1L;

        UpdateWorkRelationsDTO updateWorkRelationsDTO = new UpdateWorkRelationsDTO(expectedId,"Manager");

        Mockito.doReturn(true).when(workRelationsMockRepository).existEntityById(Mockito.any());

        workRelationsService.updateWorkRelations(updateWorkRelationsDTO);

        ArgumentCaptor<WorkRelations> argumentCaptor = ArgumentCaptor.forClass(WorkRelations.class);
        Mockito.verify(workRelationsMockRepository).updateWorkRelations(argumentCaptor.capture());

        WorkRelations workRelations = argumentCaptor.getValue();
        Assertions.assertEquals(expectedId, workRelations.getId());
    }

    @Test
    void findById() throws NotFoundException {
        Long expectedId = 1L;

        Optional<WorkRelations> department = Optional.of(new WorkRelations(expectedId, "Manager", null));

        Mockito.doReturn(true).when(workRelationsMockRepository).existEntityById(Mockito.any());
        Mockito.doReturn(department).when(workRelationsMockRepository).findRelationsById(Mockito.anyLong());

        OutgoingWorkRelationsDTO workRelationsDTO = workRelationsService.findWorkRelationsById(expectedId);

        Assertions.assertEquals(expectedId, workRelationsDTO.getId());
    }

    @Test
    void findAll() {
        workRelationsService.findAllWorkRelations();
        Mockito.verify(workRelationsMockRepository).findAllRelations();
    }

    @Test
    void delete() throws NotFoundException {
        Long expectedId = 10L;

        Mockito.doReturn(true).when(workRelationsMockRepository).existEntityById(Mockito.any());
        workRelationsService.deleteWorkRelationsById(expectedId);

        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(workRelationsMockRepository).deleteRelationsById(argumentCaptor.capture());

        Long result = argumentCaptor.getValue();
        Assertions.assertEquals(expectedId, result);
    }
}

