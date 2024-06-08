package com.example.app.dto;

/**
 * This class is used to receive work relations information from external sources
 * and map it to a standard format before it's processed or persisted in the system.
 */
public class IncomingComputerNumberDTO {

    private String number;

    public IncomingComputerNumberDTO(String number) {
        this.number = number;
    }

    public IncomingComputerNumberDTO() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
