package com.example.app.dto;

import com.example.app.model.Role;

/**
 * This class represents a Data Transfer Object (DTO) for incoming worker data.
 * It encapsulates the information required to create a new worker object.
 */
public class IncomingWorkerDTO {

    private String firstName;
    private String lastName;
    private Role role;

    public IncomingWorkerDTO(String firstName, String lastName, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public IncomingWorkerDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
