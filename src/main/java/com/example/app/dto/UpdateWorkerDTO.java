package com.example.app.dto;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing an update request for a worker.
 * This DTO is used to encapsulate the full data required to update an existing worker in the system.
 * It includes fields for the worker's ID, first name, last name, role, computer numbers, and relations.
 */
public class UpdateWorkerDTO {

    private Long id;
    private String firstName;
    private String lastName;

    private UpdateRoleDTO updateRoleDTO;

    private List<UpdateComputerNumberDTO> updateComputerNumberDTOList;
    private List<UpdateWorkRelationsDTO> updateWorkerRelationsDTOS;

    public UpdateWorkerDTO(Long id, String firstName, String lastName, UpdateRoleDTO updateRoleDTO,
                           List<UpdateComputerNumberDTO> updateComputerNumberDTOList,
                           List<UpdateWorkRelationsDTO> updateWorkerRelationsDTOS) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.updateRoleDTO = updateRoleDTO;
        this.updateComputerNumberDTOList = updateComputerNumberDTOList;
        this.updateWorkerRelationsDTOS = updateWorkerRelationsDTOS;
    }

    public UpdateWorkerDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UpdateRoleDTO getUpdateRoleDTO() {
        return updateRoleDTO;
    }

    public void setUpdateRoleDTO(UpdateRoleDTO updateRoleDTO) {
        this.updateRoleDTO = updateRoleDTO;
    }

    public List<UpdateComputerNumberDTO> getUpdateComputerNumberDTOList() {
        return updateComputerNumberDTOList;
    }

    public void setUpdateComputerNumberDTOList(List<UpdateComputerNumberDTO> updateComputerNumberDTOList) {
        this.updateComputerNumberDTOList = updateComputerNumberDTOList;
    }

    public List<UpdateWorkRelationsDTO> getUpdateWorkerRelationsDTOS() {
        return updateWorkerRelationsDTOS;
    }

    public void setUpdateWorkerRelationsDTOS(List<UpdateWorkRelationsDTO> updateWorkerRelationsDTOS) {
        this.updateWorkerRelationsDTOS = updateWorkerRelationsDTOS;
    }
}
