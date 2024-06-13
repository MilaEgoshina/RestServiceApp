package com.example.app.mapper.impl;

import com.example.app.dto.IncomingRoleDTO;
import com.example.app.dto.OutgoingRoleDTO;
import com.example.app.dto.UpdateRoleDTO;
import com.example.app.mapper.RoleMapperInterface;
import com.example.app.entity.Role;

import java.util.ArrayList;
import java.util.List;

public class RoleMapper implements RoleMapperInterface {


    public RoleMapper() {
    }

    @Override
    public Role mapToEntity(IncomingRoleDTO incomingRoleDTO) {
        return new Role(null,incomingRoleDTO.getName());
    }

    @Override
    public Role update(UpdateRoleDTO updateRoleDTO) {
        return new Role(updateRoleDTO.getId(),updateRoleDTO.getName());
    }

    @Override
    public OutgoingRoleDTO mapToDTO(Role role) {
        return new OutgoingRoleDTO(role.getId(),role.getName());
    }

    @Override
    public List<OutgoingRoleDTO> mapToOutGoingDtos(List<Role> roleList) {

        List<OutgoingRoleDTO> outgoingRoleDTOS = new ArrayList<>();

        for(Role role : roleList){
            outgoingRoleDTOS.add(mapToDTO(role));
        }
        return outgoingRoleDTOS;
    }
}
