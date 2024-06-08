package com.example.app.dto;

/**
 * This class represents a DTO (Data Transfer Object) for updating a Role entity.
 * It contains the ID and name of the Role to be updated.
 */
public class UpdateRoleDTO {

    private Long id;
    private String name;

    public UpdateRoleDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public UpdateRoleDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
