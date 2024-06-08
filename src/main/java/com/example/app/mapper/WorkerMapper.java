package com.example.app.mapper;

import com.example.app.dto.IncomingWorkerDTO;
import com.example.app.dto.OutgoingFullWorkerDTO;
import com.example.app.dto.UpdateWorkerDTO;
import com.example.app.mapper.interfaces.WorkerMapperInterface;
import com.example.app.model.Worker;

import java.util.ArrayList;
import java.util.List;

public class WorkerMapper implements WorkerMapperInterface {

    private ComputerMapper computerMapper;
    private RoleMapper roleMapper;
    private WorkRelationsMapper workRelationsMapper;

    public WorkerMapper() {
        this.computerMapper = new ComputerMapper();
        this.roleMapper = new RoleMapper();
        this.workRelationsMapper = new WorkRelationsMapper();
    }

    @Override
    public Worker mapToEntity(IncomingWorkerDTO incomingWorkerDTO) {
        return new Worker(null, incomingWorkerDTO.getFirstName(), incomingWorkerDTO.getLastName(),
                incomingWorkerDTO.getRole(),null, null);
    }

    @Override
    public OutgoingFullWorkerDTO mapToDTO(Worker worker) {
        return new OutgoingFullWorkerDTO(worker.getId(),worker.getFirstName(), worker.getLastName(),
                roleMapper.mapToDTO(worker.getRole()),
                computerMapper.mapToOutGoingDtos(worker.getComputerList()),
                workRelationsMapper.mapToOutGoingDtos(worker.getWorkRelationsList()));
    }

    @Override
    public Worker update(UpdateWorkerDTO updateWorkerDTO) {
        return new Worker(updateWorkerDTO.getId(), updateWorkerDTO.getFirstName(), updateWorkerDTO.getLastName(),
                roleMapper.update(updateWorkerDTO.getUpdateRoleDTO()),
                workRelationsMapper.mapToUpdateEntities(updateWorkerDTO.getUpdateWorkerRelationsDTOS()),
                computerMapper.mapToUpdateEntities(updateWorkerDTO.getUpdateComputerNumberDTOList())
                );
    }

    @Override
    public List<OutgoingFullWorkerDTO> mapToOutGoingDtos(List<Worker> workerList) {

        List<OutgoingFullWorkerDTO> outgoingFullWorkerDTOS = new ArrayList<>();

        for(Worker worker : workerList){
            outgoingFullWorkerDTOS.add(mapToDTO(worker));
        }
        return outgoingFullWorkerDTOS;
    }
}
