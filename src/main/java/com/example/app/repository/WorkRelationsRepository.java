package com.example.app.repository;

import com.example.app.config.ConnectionToDB;
import com.example.app.entity.WorkRelations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkRelationsRepository {

    private ConnectionToDB connectionToDB = ConnectionToDB.initializeConnection();

    public WorkRelationsRepository() {
    }


    public WorkRelations saveWorkRelations(WorkRelations workRelations) {

        try (Connection connection = connectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO relations (realtion_name) VALUES(?)")) {

            preparedStatement.setString(1, workRelations.getName());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                workRelations = new WorkRelations(resultSet.getLong("workRelations_id"),
                        workRelations.getName(),
                        null);
            }
            workRelations.getWorkerList();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workRelations;
    }

    public void updateWorkRelations(WorkRelations workRelations) {
        try (Connection connection = connectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE relations SET realtion_name = ? WHERE relation_id = ?;")) {

            preparedStatement.setString(1, workRelations.getName());
            preparedStatement.setLong(2, workRelations.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRelationsById(Long id) {

        try (Connection connection = connectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM relations WHERE relation_id = ?;")) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public WorkRelations findRelationsById(Long id) {
        WorkRelations workRelations = new WorkRelations();

        try (Connection connection = connectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT relation_id, relation_name FROM relations WHERE relation_id = ?")) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                workRelations.setId(resultSet.getLong("relation_id"));
                workRelations.setName(resultSet.getString("relation_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workRelations;
    }

    public List<WorkRelations> findAllRelations() {
        List<WorkRelations> workRelationsList = new ArrayList<>();
        try (Connection connection = connectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM relations;")) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                WorkRelations workRelations = new WorkRelations();
                workRelations.setId(resultSet.getLong("relation_id"));
                workRelations.setName(resultSet.getString("relation_name"));
                workRelationsList.add(workRelations);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workRelationsList;
    }
}