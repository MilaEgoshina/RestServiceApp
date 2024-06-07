package com.example.app.dto;

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
