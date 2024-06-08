package com.example.app.dto;

/**
 * This class represents a Data Transfer Object (DTO) for updating a computer serialNumber associated with a worker.
 * It encapsulates the data required to perform a computer serialNumber update operation.
 */
public class UpdateComputerDTO {

    private Long id;
    private String serialNumber;
    private Long workerId; // id of the worker associated with the computer serialNumber.

    public UpdateComputerDTO(Long id, String number, Long workerId) {
        this.id = id;
        this.serialNumber = number;
        this.workerId = workerId;
    }

    public UpdateComputerDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }
}
