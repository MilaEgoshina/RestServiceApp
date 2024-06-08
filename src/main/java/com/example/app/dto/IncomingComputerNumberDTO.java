package com.example.app.dto;

/**
 * This class represents a data transfer object for incoming computer serial numbers of workers.
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
