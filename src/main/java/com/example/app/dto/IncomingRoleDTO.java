package com.example.app.dto;

/**
 * This class represents a data transfer object for incoming role information of worker.
 * It contains a single field for the name of the Role.
 */
public class IncomingRoleDTO {

    private String name;

    public IncomingRoleDTO(String name) {
        this.name = name;
    }

    public IncomingRoleDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
