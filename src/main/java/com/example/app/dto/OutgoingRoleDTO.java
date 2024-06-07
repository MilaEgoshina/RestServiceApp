package com.example.app.dto;

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
