package com.example.app.model;

import java.util.List;

/**
 * Many To Many: WorkRelations <-> Worker
 */
public class WorkRelations {

    private Long id;
    private String name;
    private List<Worker> workerList;

    public WorkRelations(Long id, String name, List<Worker> workerList) {
        this.id = id;
        this.name = name;
        this.workerList = workerList;
    }

    public WorkRelations() {
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

    public List<Worker> getWorkerList() {
        return workerList;
    }

    public void setWorkerList(List<Worker> workerList) {
        this.workerList = workerList;
    }
}
