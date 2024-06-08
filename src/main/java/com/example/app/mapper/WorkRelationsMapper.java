package com.example.app.mapper;

import com.example.app.dto.IncomingWorkRelationsDTO;
import com.example.app.dto.OutgoingFieldsWorkerDTO;
import com.example.app.dto.OutgoingWorkRelationsDTO;
import com.example.app.dto.UpdateWorkRelationsDTO;
import com.example.app.mapper.interfaces.WorkRelationsMapperInterface;
import com.example.app.model.WorkRelations;
import com.example.app.model.Worker;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the {@link WorkRelationsMapperInterface} interface. It is responsible for mapping between
 * {@link WorkRelations} objects and their corresponding DTOs.
 * This class uses the Singleton pattern, ensuring that only one instance of the mapper is created.
 */
public class WorkRelationsMapper implements WorkRelationsMapperInterface {

    private static WorkRelationsMapperInterface workRelationsMapperInstance;

    public WorkRelationsMapper() {
    }

    /**
     * Returns the singleton instance of the WorkRelationsMapper.
     *
     * @return The singleton instance of the WorkRelationsMapper.
     */
    public static WorkRelationsMapperInterface getWorkRelationsMapperInstance(){
        if(workRelationsMapperInstance == null){
            workRelationsMapperInstance = new WorkRelationsMapper();
        }
        return workRelationsMapperInstance;
    }


    @Override
    public WorkRelations mapToEntity(IncomingWorkRelationsDTO incomingWorkRelationsDTO) {
        return new WorkRelations(null,incomingWorkRelationsDTO.getName(),null);
    }

    @Override
    public OutgoingWorkRelationsDTO mapToDTO(WorkRelations workRelations) {

        List<OutgoingFieldsWorkerDTO> outgoingFieldsWorkerDTOList = new ArrayList<>();
         for(Worker worker : workRelations.getWorkerList()){
             outgoingFieldsWorkerDTOList.add(new OutgoingFieldsWorkerDTO(worker.getId(),worker.getFirstName(),worker.getLastName()));
         }

        return new OutgoingWorkRelationsDTO(workRelations.getId(),workRelations.getName(),outgoingFieldsWorkerDTOList);
    }

    @Override
    public WorkRelations update(UpdateWorkRelationsDTO updateWorkRelationsDTO) {
        return new WorkRelations(updateWorkRelationsDTO.getId(), updateWorkRelationsDTO.getName(), null);
    }

    @Override
    public List<OutgoingWorkRelationsDTO> mapToOutGoingDtos(List<WorkRelations> workRelationsList) {

        List<OutgoingWorkRelationsDTO> outgoingFieldsWorkerDTOS = new ArrayList<>();
        for(WorkRelations workRelations : workRelationsList){
            outgoingFieldsWorkerDTOS.add(mapToDTO(workRelations));
        }
        return outgoingFieldsWorkerDTOS;
    }

    @Override
    public List<WorkRelations> mapToUpdateEntities(List<UpdateWorkRelationsDTO> updateWorkRelationsDTOS) {

        List<WorkRelations> workRelationsList = new ArrayList<>();

        for (UpdateWorkRelationsDTO updateWorkRelationsDTO : updateWorkRelationsDTOS){
            workRelationsList.add(update(updateWorkRelationsDTO));
        }
        return workRelationsList;
    }
}
