package com.example.app.model;

/**
 * One To One: ComputerNumber - Worker
 */
public class ComputerNumber {

    private Long id;
    private String number;
    private Worker worker;

    public ComputerNumber() {
    }

    public ComputerNumber(Long id, String number, Worker worker) {
        this.id = id;
        this.number = number;
        this.worker = worker;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}
