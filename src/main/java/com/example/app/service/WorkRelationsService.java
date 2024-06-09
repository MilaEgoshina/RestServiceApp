package com.example.app.service;

import com.example.app.dto.IncomingWorkRelationsDTO;
import com.example.app.dto.OutgoingWorkRelationsDTO;
import com.example.app.dto.UpdateWorkRelationsDTO;
import com.example.app.entity.WorkRelations;
import com.example.app.entity.WorkerAndRelations;
import com.example.app.exception.NotFoundException;
import com.example.app.mapper.WorkRelationsMapper;
import com.example.app.repository.WorkRelationsRepository;
import com.example.app.repository.WorkerAndRelationsRepository;
import com.example.app.repository.WorkerRepository;

import java.util.List;

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

    public void updateWorkRelations(UpdateWorkRelationsDTO updateWorkRelationsDTO) throws NotFoundException {
        if(workerRepository.existEntityById(updateWorkRelationsDTO.getId())){
            WorkRelations workRelations = workRelationsMapper.update(updateWorkRelationsDTO);
            workRelationsRepository.updateWorkRelations(workRelations);
        }else {
            throw new NotFoundException("There is not such work relations");
        }

    }

    public OutgoingWorkRelationsDTO findWorkRelationsById(Long id) throws NotFoundException {

        WorkRelations workRelations = workRelationsRepository.findRelationsById(id);
        if(workRelations != null){

            return workRelationsMapper.mapToDTO(workRelations);
        }else {
            throw new NotFoundException("There is not such work relations");
        }
    }

    public List<OutgoingWorkRelationsDTO> findAllWorkRelations(){

        List<WorkRelations> workRelationsList = workRelationsRepository.findAllRelations();
        return workRelationsMapper.mapToOutGoingDtos(workRelationsList);
    }

    public void deleteWorkRelationsById(Long id){

        workRelationsRepository.deleteRelationsById(id);
    }

    public void addWorkerToRelations(Long id, Long workerId) throws NotFoundException {

        if(workerRepository.existEntityById(workerId)){
            WorkerAndRelations workerAndRelations = new WorkerAndRelations(null, workerId,id);
            workerAndRelationsRepository.saveWorkerAndRelations(workerAndRelations);
        }else {
            throw new NotFoundException("There is not such worker");
        }
    }

    public void deleteWorkerFromRelations(Long id, Long workerId) throws NotFoundException {

        if(workerRepository.existEntityById(workerId)) {
            WorkerAndRelations workerAndRelations = workerAndRelationsRepository.findByWorkerIdAndRelationId(workerId, id);
            if (workerAndRelations != null) {
                workerAndRelationsRepository.deleteWorkerAndRelationsById(workerAndRelations.getId());
            } else {
                throw new NotFoundException("There is not such worker");
            }
        }
    }

}
