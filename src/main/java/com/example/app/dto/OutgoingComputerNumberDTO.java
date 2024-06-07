package com.example.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OutgoingComputerNumberDTO {

    private Long id;
    private String number;

    @JsonProperty("user")
    private OutgoingFieldsWorkerDTO outgoingFieldsWorkerDTO;

    public OutgoingComputerNumberDTO(Long id, String number, OutgoingFieldsWorkerDTO outgoingFieldsWorkerDTO) {
        this.id = id;
        this.number = number;
        this.outgoingFieldsWorkerDTO = outgoingFieldsWorkerDTO;
    }

    public OutgoingComputerNumberDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public OutgoingFieldsWorkerDTO getOutgoingFieldsWorkerDTO() {
        return outgoingFieldsWorkerDTO;
    }
}
