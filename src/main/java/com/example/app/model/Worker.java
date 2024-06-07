package com.example.app.model;

import java.util.List;

public class Worker {

    private Long id;
    private String firstName;
    private String lastName;
    private Role role;
    private List<WorkRelations> workRelationsList;
    private List<ComputerNumber> computerNumberList;

    public Worker(Long id, String firstName, String lastName, Role role, List<WorkRelations> workRelationsList, List<ComputerNumber> computerNumberList) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.workRelationsList = workRelationsList;
        this.computerNumberList = computerNumberList;
    }

    public Worker() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<WorkRelations> getWorkRelationsList() {
        return workRelationsList;
    }

    public void setWorkRelationsList(List<WorkRelations> workRelationsList) {
        this.workRelationsList = workRelationsList;
    }

    public List<ComputerNumber> getComputerNumberList() {
        return computerNumberList;
    }

    public void setComputerNumberList(List<ComputerNumber> computerNumberList) {
        this.computerNumberList = computerNumberList;
    }
}
