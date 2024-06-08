package com.example.app.mapper.interfaces;

import com.example.app.dto.IncomingRoleDTO;
import com.example.app.dto.OutgoingRoleDTO;
import com.example.app.dto.UpdateRoleDTO;
import com.example.app.entity.Role;

import java.util.List;

public interface RoleMapperInterface {

    Role mapToEntity(IncomingRoleDTO incomingRoleDTO);

    Role update(UpdateRoleDTO updateRoleDTO);

    OutgoingRoleDTO mapToDTO(Role role);

    List<OutgoingRoleDTO> mapToOutGoingDtos(List<Role> roleList);
}
