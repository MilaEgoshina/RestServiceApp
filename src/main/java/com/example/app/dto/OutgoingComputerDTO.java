package com.example.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *   This class is used to transfer computer serialNumber data between the application layers.
 *   It includes the computer serialNumber itself, its unique identifier, and a reference to the associated worker.
 *
 *   The worker information is represented by a {@link OutgoingFieldsWorkerDTO} object, which provides basic details
 *   about the worker.
 */
public class OutgoingComputerDTO {

    private Long id;
    private String serialNumber;

    @JsonProperty("worker")
    private OutgoingFieldsWorkerDTO outgoingFieldsWorkerDTO;

    public OutgoingComputerDTO(Long id, String number, OutgoingFieldsWorkerDTO outgoingFieldsWorkerDTO) {
        this.id = id;
        this.serialNumber = number;
        this.outgoingFieldsWorkerDTO = outgoingFieldsWorkerDTO;
    }

    public OutgoingComputerDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public OutgoingFieldsWorkerDTO getOutgoingFieldsWorkerDTO() {
        return outgoingFieldsWorkerDTO;
    }
}
