package com.github.JoseAngelGiron.model.dao;

import com.github.JoseAngelGiron.model.entity.Admin;
import com.github.JoseAngelGiron.model.entity.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AdminDAO extends Admin implements DAO<Admin, String> {

    private static final String INSERT = "INSERT INTO ADMIN (IDAdmin, ISAdmin) VALUES (?,?)";
    private final static String UPDATE = "UPDATE ADMIN SET ISAdmin=? WHERE IDAdmin=?;";
    private final static String DELETE= "DELETE FROM ADMIN WHERE IDAdmin=?";

    private static Connection connection;

    public AdminDAO() {
    }

    public AdminDAO(User user){
        super(user.getId(), user.getName(), user.getPassword(), user.getPhoto(), user.getUserName(), user.getSurname(),
                user.getEmail(), user.getDni(), user.getAddress());
    }

    @Override
    public void save() {

    }

    @Override
    public void delete() throws SQLException {

    }

    @Override
    public boolean insert() {

        boolean inserted = false;
        if(name != null && password != null && email != null){
            try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {

                preparedStatement.setInt(1, id);
                preparedStatement.setInt(2, 1);

                preparedStatement.executeQuery();
                inserted = true;
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return inserted;

    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public Admin findById(int key) {
        return null;
    }

    @Override
    public List<Admin> findAll() {
        return List.of();
    }

    @Override
    public void close() throws IOException {

    }
}
