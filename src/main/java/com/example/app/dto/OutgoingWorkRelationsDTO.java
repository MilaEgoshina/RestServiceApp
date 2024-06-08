package com.example.app.dto;

import java.util.List;

/**
 * This class represents a Work Relations object in a simplified format for outgoing data transfer.
 * It contains basic information about the relations, including its ID, name, and a list of associated workers in a
 * simplified format.
 */
public class OutgoingWorkRelationsDTO {

    private Long id;
    private String name;
    /**
     * A list of {@link OutgoingFieldsWorkerDTO} objects representing worker associated with his relations.
     * This list provides a simplified view of worker information relevant to the worker relation context.
     */
    private List<OutgoingFieldsWorkerDTO> outgoingFieldsWorkerDTOList;

    public OutgoingWorkRelationsDTO(Long id, String name, List<OutgoingFieldsWorkerDTO> outgoingFieldsWorkerDTOList) {
        this.id = id;
        this.name = name;
        this.outgoingFieldsWorkerDTOList = outgoingFieldsWorkerDTOList;
    }

    public OutgoingWorkRelationsDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OutgoingFieldsWorkerDTO> getOutgoingFieldsWorkerDTOList() {
        return outgoingFieldsWorkerDTOList;
    }

    public void setOutgoingFieldsWorkerDTOList(List<OutgoingFieldsWorkerDTO> outgoingFieldsWorkerDTOList) {
        this.outgoingFieldsWorkerDTOList = outgoingFieldsWorkerDTOList;
    }
}
