package com.example.app.repository;

import com.example.app.config.ConnectionToDB;
import com.example.app.entity.Computer;
import com.example.app.entity.Worker;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComputerRepository {

    ConnectionToDB connectionToDB = ConnectionToDB.initializeConnection();
    public ComputerRepository() {

    }

    public Computer saveComputer(Computer computer) {

        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO computers (serial_number, worker_id) VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, computer.getSerialNumber());

            if(computer.getWorker() == null){

                preparedStatement.setNull(2, Types.NULL);
            }else {
                preparedStatement.setLong(2,computer.getWorker().getId());
            }
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                computer = new Computer(resultSet.getLong("computer_id"),
                        computer.getSerialNumber(),
                        computer.getWorker());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return computer;
    }

    public void updateComputer(Computer computer) {
        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(" UPDATE computers SET serial_number = ?, computer_id = ? WHERE computer_id = ?;")) {

            preparedStatement.setString(1, computer.getSerialNumber());
            if(computer.getWorker() == null){

                preparedStatement.setNull(2, Types.NULL);
            }else {
                preparedStatement.setLong(2,computer.getWorker().getId());
            }

            preparedStatement.setLong(3,computer.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteComputerById(Long id) {

        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM computers WHERE computer_id = ?;")) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteComputerByWorkerId(Long workerId) {

        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM computers WHERE worker_id = ?;")) {

            preparedStatement.setLong(1, workerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Computer findComputerById(Long id) {
        Computer computer = null;

        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT computer_id, serial_number, worker_id FROM computers WHERE computer_id = ?;")) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                computer = createComputer(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return computer;
    }

    public Computer findComputerBySerialNumber(String serialNumber) {
        Computer computer = null;
        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT computer_id, serial_number, worker_id FROM computers WHERE serial_number = ?;")) {

            preparedStatement.setString(1, serialNumber);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                computer = createComputer(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return computer;
    }

    public List<Computer> findAllComputers() {
        List<Computer> computerList = new ArrayList<>();
        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM computers;")) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                computerList.add(createComputer(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return computerList;
    }

    public List<Computer> findAllComputersByWorkerId(Long workerId){
        List<Computer> computerList = new ArrayList<>();
        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM computers WHERE worker_id = ?;")) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                computerList.add(createComputer(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return computerList;
    }

    public boolean existEntityById(Long id){
        boolean isExisting = true;

        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(" SELECT exists (SELECT 1 FROM computers WHERE computer_id = ? LIMIT 1);")){

            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                isExisting = resultSet.getBoolean(1);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return isExisting;
    }

    private Computer createComputer(ResultSet resultSet) throws SQLException{
        Computer computer = new Computer();
        Worker worker = new Worker(resultSet.getLong("worker_id"),
                null,null,null,null,null);
        computer.setId(resultSet.getLong("computer_id"));
        computer.setSerialNumber(resultSet.getString("serial_number"));
        computer.setWorker(worker);
        return computer;
    }
}
