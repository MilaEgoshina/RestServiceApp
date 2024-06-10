package com.example.app.repository;

import com.example.app.config.ConnectionToDB;
import com.example.app.entity.WorkerAndRelations;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkerAndRelationsRepository {

    private final ConnectionToDB connectionToDB = ConnectionToDB.initialize();
    private WorkerRepository workerRepository;
    private WorkRelationsRepository workRelationsRepository;

    public WorkerAndRelationsRepository() {

        this.workerRepository = new WorkerRepository();
        this.workRelationsRepository = new WorkRelationsRepository();
    }


    public WorkerAndRelations saveWorkerAndRelations(WorkerAndRelations workerAndRelations) {

        try (Connection connection = connectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO worker_relations (worker_id, relation_id) VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setLong(1, workerAndRelations.getWorkerId());
            preparedStatement.setLong(2,workerAndRelations.getRelationId());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                workerAndRelations = new WorkerAndRelations(resultSet.getLong("worker_relations_id"),
                        workerAndRelations.getWorkerId(),
                        workerAndRelations.getRelationId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workerAndRelations;
    }

    public void updateWorkerAndRelations(WorkerAndRelations workerAndRelations) {

        try (Connection connection = connectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE worker_relations SET worker_id= ?, relation_id =? WHERE worker_relations_id = ?;")) {

            preparedStatement.setLong(1, workerAndRelations.getWorkerId());
            preparedStatement.setLong(2, workerAndRelations.getRelationId());
            preparedStatement.setLong(3, workerAndRelations.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteWorkerAndRelationsById(Long id) {

        try (Connection connection = connectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM worker_relations WHERE worker_relations_id = ?;")) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteWorkerAndRelationsByWorkerId(Long workerId){
        try (Connection connection = connectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM worker_relations WHERE worker_id = ?;")) {

            preparedStatement.setLong(1, workerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteWorkerAndRelationsByRelationId(Long relationId){
        try (Connection connection = connectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM worker_relations WHERE relation_id = ?;")) {

            preparedStatement.setLong(1, relationId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public WorkerAndRelations findWorkerAndRelationsById(Long id) {
        WorkerAndRelations workerAndRelations = null;

        try (Connection connection = connectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT worker_relations_id, worker_id, relation_id FROM worker_relations WHERE worker_relations_id = ?;")) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                workerAndRelations = createWorkerAndRelations(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workerAndRelations;
    }

    public WorkerAndRelations findByWorkerIdAndRelationId(Long workerId, Long relationId){
        WorkerAndRelations workerAndRelations = null;

        try (Connection connection = connectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT worker_relations_id, worker_id, relation_id FROM workerAndRelations WHERE worker_id = ? AND relation_id = ?;")) {

            preparedStatement.setLong(1, workerId);
            preparedStatement.setLong(1, relationId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                workerAndRelations = createWorkerAndRelations(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workerAndRelations;
    }

    public List<WorkerAndRelations> findAllWorkerAndRelations() {
        List<WorkerAndRelations> workerAndRelationsList = new ArrayList<>();
        try (Connection connection = connectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM worker_relations;")) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                workerAndRelationsList.add(createWorkerAndRelations(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workerAndRelationsList;
    }

    public List<WorkerAndRelations> findAllWorkerAndRelationsByWorkerId(Long workerId) {
        List<WorkerAndRelations> workerAndRelationsList = new ArrayList<>();
        try (Connection connection = connectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM worker_relations WHERE worker_id = ?;")) {

            preparedStatement.setLong(1, workerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                workerAndRelationsList.add(createWorkerAndRelations(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workerAndRelationsList;
    }

    public List<WorkerAndRelations> findAllWorkerAndRelationsByRelationId(Long relationId) {
        List<WorkerAndRelations> workerAndRelationsList = new ArrayList<>();
        try (Connection connection = connectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM worker_relations WHERE relation_id = ?;")) {

            preparedStatement.setLong(1, relationId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                workerAndRelationsList.add(createWorkerAndRelations(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workerAndRelationsList;
    }
    private WorkerAndRelations createWorkerAndRelations(ResultSet resultSet) throws SQLException{
        WorkerAndRelations workerAndRelations = new WorkerAndRelations(
                resultSet.getLong("worker_relations_id"),
                resultSet.getLong("worker_id"),
                resultSet.getLong("relation_id")
        );

        return workerAndRelations;
    }
}
