package com.example.app.service;

import com.example.app.dto.IncomingWorkerDTO;
import com.example.app.dto.OutgoingFullWorkerDTO;
import com.example.app.dto.UpdateWorkerDTO;
import com.example.app.entity.Worker;
import com.example.app.exception.NotFoundException;
import com.example.app.mapper.impl.WorkerMapper;
import com.example.app.repository.WorkerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WorkerServiceTest {

    @Mock
    private WorkerRepository workerRepository;

    @Mock
    private WorkerMapper workerMapper;

    @InjectMocks
    private WorkerService workerService;

    @Before
    public void setup() {

        when(workerMapper.mapToEntity(any())).thenReturn(new Worker());
        when(workerMapper.mapToDTO(any())).thenReturn(new OutgoingFullWorkerDTO());

        when(workerRepository.saveWorker(any())).thenReturn(new Worker());
        when(workerRepository.existEntityById(anyLong())).thenReturn(true);
        when(workerRepository.findWorkerById(anyLong())).thenReturn(new Worker());
        when(workerRepository.findAllWorkers()).thenReturn(Arrays.asList(new Worker()));
    }

    @Test
    public void testSaveWorker() {
        IncomingWorkerDTO incomingWorkerDTO = new IncomingWorkerDTO();
        OutgoingFullWorkerDTO result = workerService.saveWorker(incomingWorkerDTO);
        assertEquals(OutgoingFullWorkerDTO.class, result.getClass());
        verify(workerMapper, times(1)).mapToEntity(any());
        verify(workerRepository, times(1)).saveWorker(any());
        verify(workerMapper, times(1)).mapToDTO(any());
    }

    @Test
    public void testUpdateWorker() throws NotFoundException {
        UpdateWorkerDTO updateWorkerDTO = new UpdateWorkerDTO();
        updateWorkerDTO.setId(1L);
        workerService.updateWorker(updateWorkerDTO);
        verify(workerRepository, times(1)).existEntityById(anyLong());
        verify(workerMapper, times(1)).update(any());
        verify(workerRepository, times(1)).updateWorker(any());
    }

    @Test
    public void testFindWorkerById() throws NotFoundException {
        OutgoingFullWorkerDTO result = workerService.findWorkerById(1L);
        assertEquals(OutgoingFullWorkerDTO.class, result.getClass());
        verify(workerRepository, times(1)).findWorkerById(anyLong());
        verify(workerMapper, times(1)).mapToDTO(any());
    }

    @Test
    public void testFindAllWorker() {
        List<OutgoingFullWorkerDTO> result = workerService.findAllWorker();
        assertEquals(1, result.size());
        verify(workerRepository, times(1)).findAllWorkers();
        verify(workerMapper, times(1)).mapToOutGoingDtos(anyList());
    }

    @Test
    public void testDeleteWorkerById() {
        workerService.deleteWorkerById(1L);
        verify(workerRepository, times(1)).deleteWorkerById(anyLong());
    }
}
