package com.example.app.mapper;

import com.example.app.dto.IncomingRoleDTO;
import com.example.app.dto.OutgoingRoleDTO;
import com.example.app.dto.UpdateRoleDTO;
import com.example.app.mapper.interfaces.RoleMapperInterface;
import com.example.app.model.Role;

import java.util.ArrayList;
import java.util.List;

public class RoleMapper implements RoleMapperInterface {

    private static RoleMapperInterface roleMapperInstance;

    public RoleMapper() {
    }

    private static RoleMapperInterface getRoleMapperInstance(){
        if(roleMapperInstance == null){
            roleMapperInstance = new RoleMapper();
        }
        return roleMapperInstance;
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
