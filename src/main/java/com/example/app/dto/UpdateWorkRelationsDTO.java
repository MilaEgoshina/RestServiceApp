package com.example.app.dto;

/**
 * Data Transfer Object (DTO) representing the updated data for a worker relations.
 * This DTO is used to receive and process data for updating an existing relations.
 */
public class UpdateWorkRelationsDTO {

    private Long id;
    private String name;

    public UpdateWorkRelationsDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public UpdateWorkRelationsDTO() {
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
