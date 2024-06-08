package com.example.app.mapper.interfaces;

import com.example.app.dto.IncomingComputerNumberDTO;
import com.example.app.dto.OutgoingComputerNumberDTO;
import com.example.app.dto.UpdateComputerNumberDTO;
import com.example.app.model.ComputerNumber;

/**
 * This interface defines methods for mapping between DTOs (Data Transfer Objects)
 * and the {@link ComputerNumber} entity.
 * It handles the conversion of data between the incoming request DTOs, outgoing response DTOs,
 * and the internal {@link ComputerNumber} model.
 */
import java.util.List;

public interface ComputerNumberMapperInterface {

    ComputerNumber mapToEntity(IncomingComputerNumberDTO incomingComputerNumberDTO);

    OutgoingComputerNumberDTO mapToDTO(ComputerNumber computerNumber);

    ComputerNumber update(UpdateComputerNumberDTO updateComputerNumberDTO);

    List<OutgoingComputerNumberDTO> mapToOutGoingDtos(List<ComputerNumber> computerNumberList);

    List<ComputerNumber> mapToUpdateEntities(List<UpdateComputerNumberDTO> updateComputerNumberDTOS);

}
