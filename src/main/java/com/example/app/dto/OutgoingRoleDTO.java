package com.example.app.dto;

/**
 * Represents a Role as it is exposed to the outside world.
 * This class is a DTO (Data Transfer Object) that is used to transfer role information
 * between the server and the client.
 */
public class OutgoingRoleDTO {

    private Long id;
    private String name;

    public OutgoingRoleDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public OutgoingRoleDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
