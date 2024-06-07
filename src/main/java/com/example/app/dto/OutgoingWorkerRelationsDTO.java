package com.example.app.dto;

import java.util.List;

public class OutgoingWorkerRelationsDTO {

    private Long id;
    private String name;
    private List<OutgoingFieldsWorkerDTO> outgoingFieldsWorkerDTOList;

    public OutgoingWorkerRelationsDTO(Long id, String name, List<OutgoingFieldsWorkerDTO> outgoingFieldsWorkerDTOList) {
        this.id = id;
        this.name = name;
        this.outgoingFieldsWorkerDTOList = outgoingFieldsWorkerDTOList;
    }

    public OutgoingWorkerRelationsDTO() {
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
