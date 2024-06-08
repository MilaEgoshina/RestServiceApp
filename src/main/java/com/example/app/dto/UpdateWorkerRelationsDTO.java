package com.example.app.dto;

/**
 * Data Transfer Object (DTO) representing the updated data for a worker relations.
 * This DTO is used to receive and process data for updating an existing relations.
 */
public class UpdateWorkerRelationsDTO {

    private Long id;
    private String name;

    public UpdateWorkerRelationsDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public UpdateWorkerRelationsDTO() {
    }


}
