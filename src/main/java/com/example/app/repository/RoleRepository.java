package com.example.app.repository;

import com.example.app.config.ConnectionToDB;
import com.example.app.entity.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleRepository {

    private ConnectionToDB connectionToDB = ConnectionToDB.initializeConnection();
    public RoleRepository() {
    }

    public Role saveRole(Role role) {

        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO roles (role_name) VALUES (?);", Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, role.getName());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                role = new Role(resultSet.getLong("role_id"),
                        role.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return role;
    }

    public void updateRole(Role role) {
        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE roles SET role_name = ? WHERE role_id = ?;")) {

            preparedStatement.setString(1, role.getName());

            preparedStatement.setLong(2,role.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRoleById(Long id) {

        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM roles WHERE role_id = ?;")) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Role findRoleById(Long id) {
        Role role = new Role();

        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT role_id, role_name FROM roles WHERE role_id = ?")) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                role.setId(resultSet.getLong("role_id"));
                role.setName(resultSet.getString("role_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return role;
    }

    public List<Role> findAllRoles() {
        List<Role> roleList = new ArrayList<>();
        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM roles;")) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Role role = new Role();
                role.setId(resultSet.getLong("role_id"));
                role.setName(resultSet.getString("role_name"));
                roleList.add(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roleList;
    }
    public boolean existEntityById(Long id){
        boolean isExisting = true;

        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(" SELECT exists (SELECT 1 FROM roles WHERE role_id = ? LIMIT 1);")){

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
}
