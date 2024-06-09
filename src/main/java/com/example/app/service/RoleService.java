package com.example.app.service;

import com.example.app.dto.IncomingRoleDTO;
import com.example.app.dto.OutgoingRoleDTO;
import com.example.app.dto.UpdateRoleDTO;
import com.example.app.entity.Role;
import com.example.app.exception.NotFoundException;
import com.example.app.mapper.RoleMapper;
import com.example.app.repository.RoleRepository;

import java.util.List;

public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    public RoleService(){
        this.roleRepository = new RoleRepository();
        this.roleMapper = new RoleMapper();
    }

    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public OutgoingRoleDTO saveRole(IncomingRoleDTO incomingRoleDTO){

        Role role = roleMapper.mapToEntity(incomingRoleDTO);
        role = roleRepository.saveRole(role);
        return roleMapper.mapToDTO(role);
    }
    public void updateRole(UpdateRoleDTO updateRoleDTO) throws NotFoundException {

        if(roleRepository.existEntityById(updateRoleDTO.getId())){
            Role role = roleMapper.update(updateRoleDTO);
            roleRepository.updateRole(role);
        }else {
            throw new NotFoundException("There is not such role");
        }
    }
    public OutgoingRoleDTO findRoleById(Long id) throws NotFoundException {

        Role role = roleRepository.findRoleById(id);
        if(role != null){

            return roleMapper.mapToDTO(role);
        }else {
            throw new NotFoundException("There is not such work relations");
        }
    }
    public List<OutgoingRoleDTO> findAllRole(){

        List<Role> roleList = roleRepository.findAllRoles();
        return roleMapper.mapToOutGoingDtos(roleList);
    }
    public void deleteRoleById(Long id){

        roleRepository.deleteRoleById(id);
    }
}
