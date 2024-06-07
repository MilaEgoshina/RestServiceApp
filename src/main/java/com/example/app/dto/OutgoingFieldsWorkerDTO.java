package com.example.app.dto;

/**
 * This class is a Data Transfer Object (DTO) used to transfer a minimal set of worker information (ID, first name, last name)
 * between different layers of an application, such as from the database to the presentation layer.
 */
public class OutgoingFieldsWorkerDTO {

    private Long id;
    private String firstName;
    private String lastName;

    public OutgoingFieldsWorkerDTO(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public OutgoingFieldsWorkerDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}
