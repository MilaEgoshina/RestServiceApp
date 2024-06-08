package com.example.app.dto;

/**
 * This class represents a Data Transfer Object (DTO) for updating a computer number associated with a worker.
 * It encapsulates the data required to perform a computer number update operation.
 */
public class UpdateComputerNumberDTO {

    private Long id;
    private String number;
    private Long workerId; // id of the worker associated with the computer number.

    public UpdateComputerNumberDTO(Long id, String number, Long workerId) {
        this.id = id;
        this.number = number;
        this.workerId = workerId;
    }

    public UpdateComputerNumberDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }
}
