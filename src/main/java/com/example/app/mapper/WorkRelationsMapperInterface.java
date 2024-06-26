package com.example.app.mapper;

import com.example.app.dto.IncomingWorkRelationsDTO;
import com.example.app.dto.OutgoingWorkRelationsDTO;
import com.example.app.dto.UpdateWorkRelationsDTO;
import com.example.app.entity.WorkRelations;

import java.util.List;

public interface WorkRelationsMapperInterface {

    WorkRelations mapToEntity(IncomingWorkRelationsDTO incomingWorkRelationsDTO);

    OutgoingWorkRelationsDTO mapToDTO(WorkRelations workRelations);

    WorkRelations update(UpdateWorkRelationsDTO updateWorkRelationsDTO);

    List<OutgoingWorkRelationsDTO> mapToOutGoingDtos(List<WorkRelations> workRelationsList);

    List<WorkRelations> mapToUpdateEntities(List<UpdateWorkRelationsDTO> updateWorkRelationsDTOS);
}
