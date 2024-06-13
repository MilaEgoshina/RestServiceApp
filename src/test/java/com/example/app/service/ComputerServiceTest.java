package com.example.app.service;

import com.example.app.dto.IncomingComputerDTO;
import com.example.app.dto.OutgoingComputerDTO;
import com.example.app.dto.UpdateComputerDTO;
import com.example.app.entity.Computer;
import com.example.app.exception.NotFoundException;
import com.example.app.mapper.impl.ComputerMapper;
import com.example.app.repository.ComputerRepository;
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
public class ComputerServiceTest {

    @Mock
    private ComputerRepository computerRepository;

    @Mock
    private ComputerMapper computerMapper;

    @InjectMocks
    private ComputerService computerService;

    @Before
    public void setup() {

        when(computerMapper.mapToEntity(any())).thenReturn(new Computer());
        when(computerMapper.mapToDTO(any())).thenReturn(new OutgoingComputerDTO());

        when(computerRepository.saveComputer(any())).thenReturn(new Computer());
        when(computerRepository.existEntityById(anyLong())).thenReturn(true);
        when(computerRepository.findComputerById(anyLong())).thenReturn(new Computer());
        when(computerRepository.findAllComputers()).thenReturn(Arrays.asList(new Computer()));
    }

    @Test
    public void testSaveComputer() {
        IncomingComputerDTO incomingComputerDTO = new IncomingComputerDTO();
        OutgoingComputerDTO result = computerService.saveComputer(incomingComputerDTO);
        assertEquals(OutgoingComputerDTO.class, result.getClass());
        verify(computerMapper, times(1)).mapToEntity(any());
        verify(computerRepository, times(1)).saveComputer(any());
        verify(computerMapper, times(1)).mapToDTO(any());
    }

    @Test
    public void testUpdateComputer() throws NotFoundException {
        UpdateComputerDTO updateComputerDTO = new UpdateComputerDTO();
        updateComputerDTO.setId(1L);
        computerService.updateComputer(updateComputerDTO);
        verify(computerRepository, times(1)).existEntityById(anyLong());
        verify(computerMapper, times(1)).update(any());
        verify(computerRepository, times(1)).updateComputer(any());
    }

    @Test
    public void testFindComputerById() throws NotFoundException {
        OutgoingComputerDTO result = computerService.findComputerById(1L);
        assertEquals(OutgoingComputerDTO.class, result.getClass());
        verify(computerRepository, times(1)).findComputerById(anyLong());
        verify(computerMapper, times(1)).mapToDTO(any());
    }

    @Test
    public void testFindAllComputer() {
        List<OutgoingComputerDTO> result = computerService.findAllComputer();
        assertEquals(1, result.size());
        verify(computerRepository, times(1)).findAllComputers();
        verify(computerMapper, times(1)).mapToOutGoingDtos(anyList());
    }

    @Test
    public void testDeleteComputerById() {
        computerService.deleteComputerById(1L);
        verify(computerRepository, times(1)).deleteComputerById(anyLong());
    }
}
