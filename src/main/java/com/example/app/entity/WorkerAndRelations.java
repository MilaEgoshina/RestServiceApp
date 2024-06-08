package com.example.app.entity;

/**
 * ManyToMany
 * Worker <-> Relation
 */
public class WorkerAndRelations {

    private Long id;
    private Long workerId;
    private Long relationId;

    public WorkerAndRelations(Long id, Long workerId, Long relationId) {
        this.id = id;
        this.workerId = workerId;
        this.relationId = relationId;
    }

    public WorkerAndRelations() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }
}
