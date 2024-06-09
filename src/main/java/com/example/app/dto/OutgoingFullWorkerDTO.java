package com.example.app.dto;

import java.util.List;

/**
 *   This class represents a Data Transfer Object (DTO) for worker full data that is
 *   sent from the server to the client.
 *
 *   It encapsulates the user's information including:
 *   - ID
 *   - First Name
 *   - Last Name
 *   - Role (as a {@link OutgoingRoleDTO})
 *   - List of Computer Numbers (as {@link OutgoingComputerDTO})
 *   - List of Worker Relations (as {@link OutgoingWorkRelationsDTO})
 */
public class OutgoingFullWorkerDTO {

    private Long id;
    private String firstName;
    private String lastName;

    private OutgoingRoleDTO outgoingRoleDTO;
    private List<OutgoingComputerDTO> outgoingComputerNumberDTOS;
    private List<OutgoingWorkRelationsDTO> outgoingWorkRelationsDTOS;

    public OutgoingFullWorkerDTO(Long id, String firstName, String lastName, OutgoingRoleDTO outgoingRoleDTO,
                                 List<OutgoingComputerDTO> outgoingComputerNumberDTOS,
                                 List<OutgoingWorkRelationsDTO> outgoingWorkRelationsDTOS) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.outgoingRoleDTO = outgoingRoleDTO;
        this.outgoingComputerNumberDTOS = outgoingComputerNumberDTOS;
        this.outgoingWorkRelationsDTOS = outgoingWorkRelationsDTOS;
    }

    public OutgoingFullWorkerDTO() {
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

    public OutgoingRoleDTO getOutgoingRoleDTO() {
        return outgoingRoleDTO;
    }

    public void setOutgoingRoleDTO(OutgoingRoleDTO outgoingRoleDTO) {
        this.outgoingRoleDTO = outgoingRoleDTO;
    }

    public List<OutgoingComputerDTO> getOutgoingComputerNumberDTOS() {
        return outgoingComputerNumberDTOS;
    }

    public void setOutgoingComputerNumberDTOS(List<OutgoingComputerDTO> outgoingComputerNumberDTOS) {
        this.outgoingComputerNumberDTOS = outgoingComputerNumberDTOS;
    }

    public List<OutgoingWorkRelationsDTO> getOutgoingWorkerRelationsDTOS() {
        return outgoingWorkRelationsDTOS;
    }

    public void setOutgoingWorkerRelationsDTOS(List<OutgoingWorkRelationsDTO> outgoingWorkerRelationsDTOS) {
        this.outgoingWorkRelationsDTOS = outgoingWorkerRelationsDTOS;
    }
}
