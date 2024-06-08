package com.example.app.dto;

/**
 * This class is used to receive work relations information from external sources
 * and map it to a standard format before it's processed or persisted in the system.
 */
public class IncomingComputerDTO {

    private String serialNumber;

    public IncomingComputerDTO(String number) {
        this.serialNumber = number;
    }

    public IncomingComputerDTO() {
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
