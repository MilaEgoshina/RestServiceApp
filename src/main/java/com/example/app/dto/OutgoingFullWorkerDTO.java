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
 *   - List of Computer Numbers (as {@link OutgoingComputerNumberDTO})
 *   - List of Worker Relations (as {@link OutgoingWorkRelationsDTO})
 */
public class OutgoingFullWorkerDTO {

    private Long id;
    private String firstName;
    private String lastName;

    private OutgoingRoleDTO outgoingRoleDTO;
    private List<OutgoingComputerNumberDTO> outgoingComputerNumberDTOS;
    private List<OutgoingWorkRelationsDTO> outgoingWorkRelationsDTOS;

    public OutgoingFullWorkerDTO(Long id, String firstName, String lastName, OutgoingRoleDTO outgoingRoleDTO,
                                 List<OutgoingComputerNumberDTO> outgoingComputerNumberDTOS,
                                 List<OutgoingWorkRelationsDTO> outgoingWorkRelationsDTOS) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.outgoingRoleDTO = outgoingRoleDTO;
        this.outgoingComputerNumberDTOS = outgoingComputerNumberDTOS;
        this.outgoingWorkRelationsDTOS = outgoingWorkRelationsDTOS;
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

    public List<OutgoingComputerNumberDTO> getOutgoingComputerNumberDTOS() {
        return outgoingComputerNumberDTOS;
    }

    public void setOutgoingComputerNumberDTOS(List<OutgoingComputerNumberDTO> outgoingComputerNumberDTOS) {
        this.outgoingComputerNumberDTOS = outgoingComputerNumberDTOS;
    }

    public List<OutgoingWorkRelationsDTO> getOutgoingWorkerRelationsDTOS() {
        return outgoingWorkRelationsDTOS;
    }

    public void setOutgoingWorkerRelationsDTOS(List<OutgoingWorkRelationsDTO> outgoingWorkerRelationsDTOS) {
        this.outgoingWorkRelationsDTOS = outgoingWorkerRelationsDTOS;
    }
}
