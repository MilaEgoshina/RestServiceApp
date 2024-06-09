package com.example.app.service;

import com.example.app.dto.IncomingRoleDTO;
import com.example.app.dto.OutgoingRoleDTO;
import com.example.app.dto.UpdateRoleDTO;
import com.example.app.entity.Role;
import com.example.app.exception.NotFoundException;
import com.example.app.mapper.RoleMapper;
import com.example.app.repository.RoleRepository;
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
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private RoleService roleService = new RoleService(roleRepository, roleMapper);

    @Before
    public void setup() {

        when(roleMapper.mapToEntity(any())).thenReturn(new Role());
        when(roleMapper.mapToDTO(any())).thenReturn(new OutgoingRoleDTO());

        when(roleRepository.saveRole(any())).thenReturn(new Role());
        when(roleRepository.existEntityById(anyLong())).thenReturn(true);
        when(roleRepository.findRoleById(anyLong())).thenReturn(new Role());
        when(roleRepository.findAllRoles()).thenReturn(Arrays.asList(new Role()));
    }

    @Test
    public void testSaveRole() {
        IncomingRoleDTO incomingRoleDTO = new IncomingRoleDTO();
        OutgoingRoleDTO result = roleService.saveRole(incomingRoleDTO);
        assertEquals(OutgoingRoleDTO.class, result.getClass());
        verify(roleMapper, times(1)).mapToEntity(any());
        verify(roleRepository, times(1)).saveRole(any());
        verify(roleMapper, times(1)).mapToDTO(any());
    }

    @Test
    public void testUpdateRole() throws NotFoundException {
        UpdateRoleDTO updateRoleDTO = new UpdateRoleDTO();
        updateRoleDTO.setId(1L);
        roleService.updateRole(updateRoleDTO);
        verify(roleRepository, times(1)).existEntityById(anyLong());
        verify(roleMapper, times(1)).update(any());
        verify(roleRepository, times(1)).updateRole(any());
    }

    @Test
    public void testFindRoleById() throws NotFoundException {
        OutgoingRoleDTO result = roleService.findRoleById(1L);
        assertEquals(OutgoingRoleDTO.class, result.getClass());
        verify(roleRepository, times(1)).findRoleById(anyLong());
        verify(roleMapper, times(1)).mapToDTO(any());
    }

    @Test
    public void testFindAllRole() {
        List<OutgoingRoleDTO> result = roleService.findAllRole();
        assertEquals(1, result.size());
        verify(roleRepository, times(1)).findAllRoles();
        verify(roleMapper, times(1)).mapToOutGoingDtos(anyList());
    }

    @Test
    public void testDeleteRoleById() {
        roleService.deleteRoleById(1L);
        verify(roleRepository, times(1)).deleteRoleById(anyLong());
    }
}

