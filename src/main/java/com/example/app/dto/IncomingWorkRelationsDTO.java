package com.example.app.dto;

/**
 * This class represents a data transfer object (DTO) for receiving worker relations information.
 * It is used to transfer data from the client to the server.
 */
public class IncomingWorkRelationsDTO {

    private String name;

    public IncomingWorkRelationsDTO(String name) {
        this.name = name;
    }

    public IncomingWorkRelationsDTO() {
    }

    public String getName() {
        return name;
    }

}
