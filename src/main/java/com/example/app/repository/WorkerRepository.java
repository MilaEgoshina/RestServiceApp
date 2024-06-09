package com.example.app.repository;

import com.example.app.config.ConnectionToDB;
import com.example.app.entity.Worker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkerRepository {

    private ConnectionToDB connectionToDB = ConnectionToDB.initializeConnection();
    private ComputerRepository computerRepository;
    private RoleRepository roleRepository;
    private WorkRelationsRepository workRelationsRepository;

    public WorkerRepository() {

        this.computerRepository = new ComputerRepository();
        this.roleRepository = new RoleRepository();
        this.workRelationsRepository = new WorkRelationsRepository();
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

            computerRepository.deleteRelationsById(id);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Worker findRelationsById(Long id) {
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

    private void saveWorkRelationsList(Worker worker){

    }
}
