package com.example.app.mapper;

import com.example.app.dto.IncomingComputerDTO;
import com.example.app.dto.OutgoingComputerDTO;
import com.example.app.dto.OutgoingFieldsWorkerDTO;
import com.example.app.dto.UpdateComputerDTO;
import com.example.app.mapper.interfaces.ComputerMapperInterface;
import com.example.app.entity.Computer;
import com.example.app.entity.Worker;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for mapping Computer objects to and from their DTO representations.
 * The class implements the Singleton design pattern, ensuring only one instance of the mapper exists.
 */
public class ComputerMapper implements ComputerMapperInterface {


    public ComputerMapper() {
    }

    /**
     * Maps a IncomingComputerDTO object to a Computer object.
     * This method is used to create a new Computer object from incoming data.
     * It sets the number and sets the worker to null, as the worker information is not available during creation.
     *
     * @param incomingComputerDTO The IncomingComputerDTO object to map.
     * @return The mapped Computer object.
     */
    @Override
    public Computer mapToEntity(IncomingComputerDTO incomingComputerDTO) {
        return new Computer(null,incomingComputerDTO.getSerialNumber(),null);
    }

    /**
     * Maps a Computer object to a OutgoingComputerDTO object.
     * This method is used to create a DTO representation of a Computer object for outgoing purposes.
     * It includes the serial number, ID, and a OutgoingFieldsWorkerDTO representing the associated user.
     * If the Computer does not have an associated worker, the worker field in the DTO will be null.
     *
     * @param computer The Computer object to map.
     * @return The mapped OutgoingComputerDTO object.
     */
    @Override
    public OutgoingComputerDTO mapToDTO(Computer computer) {
        return new OutgoingComputerDTO(computer.getId(), computer.getSerialNumber(),
                computer.getWorker() == null ? null : new OutgoingFieldsWorkerDTO(
                        computer.getWorker().getId(),
                        computer.getWorker().getFirstName(),
                        computer.getWorker().getLastName()
                ));
    }

    /**
     *  This method does not actually update the Computer entity in the database or other data store.
     *  It only creates a new Computer object with the updated information.
     *  The Worker object created in this method is only used for associating the Computer with a specific
     *  Worker and does not represent a complete Worker entity.
     *
     * @param updateComputerDTO An instance of UpdateComputerDTO containing the data to be used for
     *                          updating the Computer entity.
     * @return A new instance of Computer with the updated information from the updateComputerDTO.
     */
    @Override
    public Computer update(UpdateComputerDTO updateComputerDTO) {
        return new Computer(
                updateComputerDTO.getId(), updateComputerDTO.getSerialNumber(), new Worker(
                updateComputerDTO.getWorkerId(),
                null,
                null,
                null,
                null,
                null
        )
        );
    }

    /**
     * Maps a list of Computer objects to a list of OutgoingComputerDTO objects.
     * This method is used to create a list of DTO representations of Computer objects for outgoing purposes.
     *
     * @param computerList The list of Computer objects to map.
     * @return The mapped list of OutgoingComputerDTO objects.
     */
    @Override
    public List<OutgoingComputerDTO> mapToOutGoingDtos(List<Computer> computerList) {
        List<OutgoingComputerDTO> result = new ArrayList<>();
        for (Computer computer : computerList) {
            result.add(mapToDTO(computer));
        }
        return result;
    }

    /**
     * Maps a list of UpdateComputerDTO objects to a list of Computer objects.
     * This method is used to create a list of Computer objects from update data.
     * It sets the number, ID, and creates a Worker object with only the worker ID.
     *
     * @param updateComputerDTOS The list of UpdateComputerDTO objects to map.
     * @return The mapped list of Computer objects.
     */
    @Override
    public List<Computer> mapToUpdateEntities(List<UpdateComputerDTO> updateComputerDTOS) {
        List<Computer> computerList = new ArrayList<>();
        for (UpdateComputerDTO updateComputerDTO : updateComputerDTOS) {
            computerList.add(update(updateComputerDTO));
        }
        return computerList;
    }
}
