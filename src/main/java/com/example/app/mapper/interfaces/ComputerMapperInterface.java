package com.example.app.mapper.interfaces;

import com.example.app.dto.IncomingComputerDTO;
import com.example.app.dto.OutgoingComputerDTO;
import com.example.app.dto.UpdateComputerDTO;
import com.example.app.entity.Computer;

/**
 * This interface defines methods for mapping between DTOs (Data Transfer Objects)
 * and the {@link Computer} entity.
 * It handles the conversion of data between the incoming request DTOs, outgoing response DTOs,
 * and the internal {@link Computer} model.
 */
import java.util.List;

public interface ComputerMapperInterface {

    Computer mapToEntity(IncomingComputerDTO incomingComputerDTO);

    OutgoingComputerDTO mapToDTO(Computer computer);

    Computer update(UpdateComputerDTO updateComputerDTO);

    List<OutgoingComputerDTO> mapToOutGoingDtos(List<Computer> computerList);

    List<Computer> mapToUpdateEntities(List<UpdateComputerDTO> updateComputerDTOS);

}
