package com.example.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *   This class is used to transfer computer number data between the application layers.
 *   It includes the computer number itself, its unique identifier, and a reference to the associated worker.
 *
 *   The worker information is represented by a {@link OutgoingFieldsWorkerDTO} object, which provides basic details
 *   about the worker.
 */
public class OutgoingComputerNumberDTO {

    private Long id;
    private String number;

    @JsonProperty("worker")
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
