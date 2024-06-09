package com.example.app.service;

import com.example.app.dto.IncomingWorkRelationsDTO;
import com.example.app.dto.OutgoingWorkRelationsDTO;
import com.example.app.dto.UpdateWorkRelationsDTO;
import com.example.app.entity.WorkRelations;
import com.example.app.mapper.WorkRelationsMapper;
import com.example.app.repository.WorkRelationsRepository;
import com.example.app.repository.WorkerAndRelationsRepository;
import com.example.app.repository.WorkerRepository;

public class WorkRelationsService {

    private WorkRelationsRepository workRelationsRepository;
    private WorkerRepository workerRepository;
    private WorkerAndRelationsRepository workerAndRelationsRepository;
    private WorkRelationsMapper workRelationsMapper;

    public WorkRelationsService() {
        this.workRelationsRepository = new WorkRelationsRepository();
        this.workerRepository = new WorkerRepository();
        this.workerAndRelationsRepository = new WorkerAndRelationsRepository();
        this.workRelationsMapper = new WorkRelationsMapper();
    }

    public OutgoingWorkRelationsDTO saveWorkRelations(IncomingWorkRelationsDTO incomingWorkRelationsDTO){

        WorkRelations workRelations = workRelationsMapper.mapToEntity(incomingWorkRelationsDTO);
        workRelations = workRelationsRepository.saveWorkRelations(workRelations);
        return workRelationsMapper.mapToDTO(workRelations);
    }

    public void updateWorkRelations(UpdateWorkRelationsDTO updateWorkRelationsDTO){

        WorkRelations workRelations = workRelationsMapper.update(updateWorkRelationsDTO);
        workRelations = workRelationsRepository.saveWorkRelations(workRelations);
    }
}
