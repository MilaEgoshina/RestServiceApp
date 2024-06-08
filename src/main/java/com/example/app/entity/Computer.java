package com.example.app.entity;

/**
 * One To One: Computer - Worker
 */
public class Computer {

    private Long id;
    private String serialNumber;
    private Worker worker;

    public Computer() {
    }

    public Computer(Long id, String number, Worker worker) {
        this.id = id;
        this.serialNumber = number;
        this.worker = worker;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}
