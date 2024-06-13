package com.example.app.service;

import com.example.app.dto.*;
import com.example.app.entity.Computer;
import com.example.app.exception.NotFoundException;
import com.example.app.mapper.impl.ComputerMapper;
import com.example.app.repository.ComputerRepository;

import java.util.List;

public class ComputerService {
    private final ComputerRepository computerRepository;
    private final ComputerMapper computerMapper;
    public ComputerService(){
        this.computerRepository = new ComputerRepository();
        this.computerMapper = new ComputerMapper();
    }
    public OutgoingComputerDTO saveComputer(IncomingComputerDTO incomingComputerDTO){

        Computer computer = computerMapper.mapToEntity(incomingComputerDTO);
        computer = computerRepository.saveComputer(computer);
        return computerMapper.mapToDTO(computer);
    }
    public void updateComputer(UpdateComputerDTO updateComputerDTO) throws NotFoundException {

        if(computerRepository.existEntityById(updateComputerDTO.getId())){
            Computer computer = computerMapper.update(updateComputerDTO);
            computerRepository.updateComputer(computer);
        }else {
            throw new NotFoundException("There is not such computer");
        }
    }
    public OutgoingComputerDTO findComputerById(Long id) throws NotFoundException {
        Computer computer = computerRepository.findComputerById(id);
        if(computer != null){
            return computerMapper.mapToDTO(computer);
        }else {
            throw new NotFoundException("There is not such work relations");
        }
    }
    public List<OutgoingComputerDTO> findAllComputer(){
        List<Computer> computerList = computerRepository.findAllComputers();
        return computerMapper.mapToOutGoingDtos(computerList);
    }
    public void deleteComputerById(Long id){
        computerRepository.deleteComputerById(id);
    }
}
