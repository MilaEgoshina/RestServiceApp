package com.example.app.repository;

import com.example.app.config.ConnectionToDB;
import com.example.app.entity.Computer;
import com.example.app.entity.WorkRelations;
import com.example.app.entity.Worker;
import com.example.app.entity.WorkerAndRelations;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkerRepository {

    private ConnectionToDB connectionToDB = ConnectionToDB.initializeConnection();
    private ComputerRepository computerRepository;
    private RoleRepository roleRepository;
    private WorkRelationsRepository workRelationsRepository;
    private WorkerAndRelationsRepository workerAndRelationsRepository;

    public WorkerRepository() {

        this.computerRepository = new ComputerRepository();
        this.roleRepository = new RoleRepository();
        this.workRelationsRepository = new WorkRelationsRepository();
        this.workerAndRelationsRepository = new WorkerAndRelationsRepository();
    }


    public Worker saveWorker(Worker worker) {

        try (Connection connection = connectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO worker (worker_firstname, worker_lastname, role_id) VALUES (?, ? ,?);", Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, worker.getFirstName());
            preparedStatement.setString(2,worker.getLastName());

            if(worker.getRole() == null){

                preparedStatement.setNull(3,Types.NULL);
            }else {

                preparedStatement.setLong(3,worker.getRole().getId());
            }
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                worker = new Worker(resultSet.getLong("worker_id"),
                        worker.getFirstName(),
                        worker.getLastName(),
                        worker.getRole(),
                        null,
                        null);
            }
            worker.getWorkRelationsList();
            worker.getComputerList();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return worker;
    }

    public void updateWorker(Worker worker) {

        try (Connection connection = connectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE workers SET worker_firstname = ?, worker_lastname = ?, role_id =? WHERE worker_id = ?;")) {

            preparedStatement.setString(1, worker.getFirstName());
            preparedStatement.setString(2, worker.getLastName());

            if(worker.getRole() == null){

                preparedStatement.setNull(3,Types.NULL);
            }else {

                preparedStatement.setLong(3,worker.getRole().getId());
            }

            preparedStatement.setLong(4, worker.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteWorkerById(Long id) {

        try (Connection connection = connectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM workers WHERE worker_id = ?;")) {

            computerRepository.deleteComputerById(id);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Worker findWorkerById(Long id) {
        Worker worker = new Worker();

        try (Connection connection = connectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT worker_id, worker_firstname, worker_lastname, role_id FROM workers WHERE worker_id = ?")) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                worker.setId(resultSet.getLong("worker_id"));
                worker.setFirstName(resultSet.getString("worker_firstname"));
                worker.setLastName(resultSet.getString("worker_lastname"));
                worker.setRole(roleRepository.findRoleById(resultSet.getLong("role_id")));
                worker.setComputerList(null);
                worker.setWorkRelationsList(null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return worker;
    }

    public List<Worker> findAllWorkers() {
        List<Worker> workerList = new ArrayList<>();
        try (Connection connection = connectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM workers;")) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Worker worker = new Worker();
                worker.setId(resultSet.getLong("worker_id"));
                worker.setFirstName(resultSet.getString("worker_firstname"));
                worker.setLastName(resultSet.getString("worker_lastname"));
                worker.setRole(roleRepository.findRoleById(resultSet.getLong("role_id")));
                worker.setComputerList(null);
                worker.setWorkRelationsList(null);
                workerList.add(worker);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workerList;
    }

    /**
     * This method is responsible for saving the worker relations list for a given worker.
     * It checks if the worker relations list is not null, then proceeds to update the worker relations .
     * If the list is null, it deletes all existing worker-to-relations mappings.
     *
     * @param worker The user for whom the department list needs to be saved.
     */
    private void saveWorkerRelationsList(Worker worker) {

        if (worker.getWorkRelationsList() != null) {
            List<Long> workerRelationsIdList = new ArrayList<>();
            for (WorkRelations workRelations : worker.getWorkRelationsList()) {
                // Extracting workRelations ids from the worker relations list
                workerRelationsIdList.add(workRelations.getId());
            }
            // Retrieving existing worker-to-relations mappings
            List<WorkerAndRelations> workerAndRelationsList = workerAndRelationsRepository.findAllWorkerAndRelationsByWorkerId(worker.getId());
            // Removing mappings for worker relations not present in the updated list
            for (WorkerAndRelations workerAndRelations : workerAndRelationsList) {
                if (!workerRelationsIdList.contains(workerAndRelations.getRelationId())) {
                    workerAndRelationsRepository.deleteWorkerAndRelationsById(workerAndRelations.getId());
                }
                // Remove the processed worker relations ID from the list
                workerRelationsIdList.remove(workerAndRelations.getRelationId());
            }
            // Adding new mappings for worker relations in the updated list
            for(Long relationId : workerRelationsIdList){
                if(workRelationsRepository.existEntityById(relationId)){
                    WorkerAndRelations workerAndRelations = new WorkerAndRelations(
                            null,
                            worker.getId(),
                            relationId
                    );
                    workerAndRelationsRepository.saveWorkerAndRelations(workerAndRelations);
                }
            }
        }else{
            // Deleting all existing worker-to-relations mappings if the worker relations list is null
            workerAndRelationsRepository.deleteWorkerAndRelationsByWorkerId(worker.getId());
        }
    }

    /**
     * This method saves a list of computers associated with a worker.
     *
     * @param worker The worker object whose computer should be saved.
     */
    private void saveComputerList(Worker worker){

        if (worker.getComputerList() != null) {
            // Create a copy of the computer list to avoid modifying the original list
            List<Computer> computerList = new ArrayList<>(worker.getComputerList());
            List<Long> computerIdList = new ArrayList<>();

            // Fetch existing computers associated with the worker
            List<Computer> existingComputers = computerRepository.findAllComputersByWorkerId(worker.getId());
            for (Computer computer : existingComputers) {
                computerIdList.add(computer.getId());
            }

            // Iterate over the computer list and process each computer
            for(int i = 0; i < computerList.size(); i++){

                Computer computer = computerList.get(i);
                // Set the worker for the computer
                computer.setWorker(worker);

                // Check if the computer already exists in the database
                if(computerIdList.contains(computer.getId())){
                    // If the computer exists, update it
                    computerRepository.updateComputer(computer);
                }else {

                    // If computer doesn't exist, save or update the existing computer
                    saveOrUpdateComputer(computer);
                }

                // Remove the processed computer from the list and the existing computer IDs list
                computerList.set(i,null);
                computerIdList.remove(computer.getId());
            }

            // Save any remaining computers that were not processed in the loop
            for(Computer computer : computerList){
                if(computer != null){
                    computer.setWorker(worker);
                    computerRepository.saveComputer(computer);
                }
            }
            // Delete any existing computers that are no longer associated with the worker
            for(Long computerId : computerIdList){
                computerRepository.deleteComputerById(computerId);
            }
        }else {
            computerRepository.deleteComputerByWorkerId(worker.getId());
        }
    }
    /**
     * Saves or updates a Computer object in the database.
     *
     * If a Computer with the same number exists in the database, it will update the
     * associated user information (if the existing Computer is linked to a user).
     * If the Computer is new, it will be saved in the database.
     *
     *   @param computer The Computer object to be saved or updated.
     */
    private void saveOrUpdateComputer(Computer computer){

        if(computerRepository.existEntityById(computer.getId())){
            Computer computer1 = computerRepository.findComputerBySerialNumber(computer.getSerialNumber());
            if(computer1 != null){
                computer = new Computer(computer1.getId(),computer1.getSerialNumber(),computer1.getWorker());
            }
            computerRepository.updateComputer(computer);
        }else{
            computerRepository.saveComputer(computer);
        }
    }
}
