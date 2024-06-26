package com.example.app.mapper;

import com.example.app.dto.IncomingWorkerDTO;
import com.example.app.dto.OutgoingFullWorkerDTO;
import com.example.app.dto.UpdateWorkerDTO;
import com.example.app.entity.Worker;

import java.util.List;

public interface WorkerMapperInterface {

    Worker mapToEntity(IncomingWorkerDTO incomingWorkerDTO);

    OutgoingFullWorkerDTO mapToDTO(Worker worker);

    Worker update(UpdateWorkerDTO updateWorkerDTO);

    List<OutgoingFullWorkerDTO> mapToOutGoingDtos(List<Worker> workerList);

}
