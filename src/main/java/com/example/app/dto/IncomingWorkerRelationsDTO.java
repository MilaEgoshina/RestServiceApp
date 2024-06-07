package com.example.app.dto;

/**
 * This class represents a data transfer object (DTO) for receiving worker relations information.
 * It is used to transfer data from the client to the server.
 */
public class IncomingWorkerRelationsDTO {

    private String name;

    public IncomingWorkerRelationsDTO(String name) {
        this.name = name;
    }

    public IncomingWorkerRelationsDTO() {
    }

    public String getName() {
        return name;
    }

}
