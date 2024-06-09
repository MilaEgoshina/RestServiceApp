package com.example.app.service;

import com.example.app.dto.IncomingWorkerDTO;
import com.example.app.dto.OutgoingFullWorkerDTO;
import com.example.app.dto.UpdateWorkerDTO;
import com.example.app.entity.Worker;
import com.example.app.exception.NotFoundException;
import com.example.app.mapper.WorkerMapper;
import com.example.app.repository.WorkerRepository;

import java.util.List;

public class WorkerService {

    private WorkerRepository workerRepository;
    private WorkerMapper workerMapper;

    public WorkerService(){
        this.workerRepository = new WorkerRepository();
        this.workerMapper = new WorkerMapper();
    }

    public OutgoingFullWorkerDTO saveWorker(IncomingWorkerDTO incomingWorkerDTO){

        Worker worker = workerMapper.mapToEntity(incomingWorkerDTO);
        worker = workerRepository.saveWorker(worker);
        return workerMapper.mapToDTO(worker);
    }

    public void updateWorker(UpdateWorkerDTO updateWorkerDTO) throws NotFoundException {

        if(workerRepository.existEntityById(updateWorkerDTO.getId())){
            Worker worker = workerMapper.update(updateWorkerDTO);
            workerRepository.updateWorker(worker);
        }else {
            throw new NotFoundException("There is not such worker");
        }
    }

    public OutgoingFullWorkerDTO findWorkerById(Long id) throws NotFoundException {

        Worker worker = workerRepository.findWorkerById(id);
        if(worker != null){

            return workerMapper.mapToDTO(worker);
        }else {
            throw new NotFoundException("There is not such work relations");
        }
    }

    public List<OutgoingFullWorkerDTO> findAllWorker(){

        List<Worker> workerList = workerRepository.findAllWorkers();
        return workerMapper.mapToOutGoingDtos(workerList);
    }

    public void deleteWorkerById(Long id){

        workerRepository.deleteWorkerById(id);
    }
}
